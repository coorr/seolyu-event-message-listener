package kr.mainstream.eventmessagelistener.listener.eventApplicant;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import kr.mainstream.eventmessagelistener.common.exception.MessageExceptionHandler;
import kr.mainstream.eventmessagelistener.domain.applicant.Applicant;
import kr.mainstream.eventmessagelistener.domain.applicant.ApplicantService;
import kr.mainstream.eventmessagelistener.domain.event.applicationIssue.EventApplicantHistory;
import kr.mainstream.eventmessagelistener.domain.event.applicationIssue.EventApplicantHistoryService;
import kr.mainstream.eventmessagelistener.domain.resumeReview.ResumeReviewService;
import kr.mainstream.eventmessagelistener.infrastructure.file.FileMetadata;
import kr.mainstream.eventmessagelistener.infrastructure.file.FileStorageService;
import kr.mainstream.eventmessagelistener.listener.ListenerService;
import kr.mainstream.eventmessagelistener.listener.authenticate.AuthenticateService;
import kr.mainstream.eventmessagelistener.listener.authenticate.InvalidTokenException;
import kr.mainstream.eventmessagelistener.listener.eventApplicant.dto.EventApplicantConsumeDto;
import kr.mainstream.eventmessagelistener.message.MessageType;
import kr.mainstream.eventmessagelistener.message.history.MessageHistoryReqDto;
import kr.mainstream.eventmessagelistener.message.history.MessageHistoryService;
import kr.mainstream.eventmessagelistener.message.history.MessageStatus;
import kr.mainstream.eventmessagelistener.message.template.EventApplicantCreateMessageTemplate;
import kr.mainstream.eventmessagelistener.message.template.TemplateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventApplicantMessageListener implements ChannelAwareMessageListener {
    private final ListenerService listenerService;
    private final ObjectMapper objectMapper;
    private final MessageHistoryService messageHistoryService;
    private final AuthenticateService authenticateService;
    private final ApplicantService applicantService;
    private final ResumeReviewService resumeReviewService;
    private final EventApplicantHistoryService eventApplicantHistoryService;
    private final FileStorageService fileStorageService;


    @Override
    @Transactional
    public void onMessage(Message message, Channel channel) throws Exception {
        MessageHistoryReqDto messageHistoryReqDto = new MessageHistoryReqDto();
        messageHistoryReqDto.init(LocalDateTime.now(), MessageType.EVENT);

        try {
            EventApplicantConsumeDto<?> consumeDto = this.resolveDto(message.getBody());
            validate(consumeDto);

            EventApplicantCreateMessageTemplate dto = (EventApplicantCreateMessageTemplate) consumeDto.getMessage();

            MockMultipartFile file = new MockMultipartFile("file", "mockFile.txt", "text/plain", dto.getFile());
            FileMetadata metadata = fileStorageService.upload(file);
            Applicant applicant = dto.toEntity(metadata.getFilePath());
            applicantService.save(applicant);
            resumeReviewService.initialize(applicant.getId());
            eventApplicantHistoryService.save(new EventApplicantHistory(dto.getEventId(), applicant.getId()));
            messageHistoryService.save(messageHistoryReqDto, MessageStatus.SUCCESS, null);
            long count = applicantService.count();
            messageHistoryReqDto.setMessage(dto.toString());
            listenerService.ack(message, channel);
        } catch (IllegalArgumentException | InvalidTokenException e) {
            log.error(e.toString());
            messageHistoryService.save(messageHistoryReqDto, MessageStatus.INVALID, e.getMessage());
            listenerService.ack(message, channel);
        } catch (Exception e) {
            messageHistoryService.save(messageHistoryReqDto, MessageStatus.FAIL, e.getMessage());
            MessageExceptionHandler.handle(message, e.getMessage(), e);
        }
    }

    private EventApplicantConsumeDto<?> resolveDto(byte[] message) {
        TemplateType templateType;
        try {
            JsonNode rootNode = objectMapper.readTree(message);
            String templateTypeString = rootNode.get("templateType").textValue();
            templateType = TemplateType.valueOf(templateTypeString);
        } catch (Exception e) {
            throw new IllegalArgumentException("wrong templateType", e);
        }

        Class<?> templateClass = templateType.getTemplateClass();

        JavaType javaType = objectMapper.getTypeFactory()
                .constructParametricType(EventApplicantConsumeDto.class, templateClass);

        try {
            return objectMapper.readValue(message, javaType);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to deserialize message", e);
        }
    }

    private void validate(EventApplicantConsumeDto<?> consumeDto) throws InvalidTokenException {
        consumeDto.validate();
        authenticateService.authenticate(consumeDto);

        if (!(consumeDto.getMessage() instanceof EventApplicantCreateMessageTemplate)) {
            throw new IllegalArgumentException("Unsupported message type: " + consumeDto.getMessage().getClass().getSimpleName());
        }
    }
}
