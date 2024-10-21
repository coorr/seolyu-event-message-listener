package kr.mainstream.eventmessagelistener.listener.eventApplicant;

import kr.mainstream.eventmessagelistener.domain.applicant.Applicant;
import kr.mainstream.eventmessagelistener.domain.applicant.ApplicantService;
import kr.mainstream.eventmessagelistener.domain.event.applicationIssue.EventApplicantHistory;
import kr.mainstream.eventmessagelistener.domain.event.applicationIssue.EventApplicantHistoryService;
import kr.mainstream.eventmessagelistener.domain.resumeReview.ResumeReviewService;
import kr.mainstream.eventmessagelistener.infrastructure.file.FileMetadata;
import kr.mainstream.eventmessagelistener.infrastructure.file.FileStorageService;
import kr.mainstream.eventmessagelistener.message.history.MessageHistoryReqDto;
import kr.mainstream.eventmessagelistener.message.history.MessageHistoryService;
import kr.mainstream.eventmessagelistener.message.history.MessageStatus;
import kr.mainstream.eventmessagelistener.message.template.EventApplicantCreateMessageTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncEventApplicantService {
    private final ApplicantService applicantService;
    private final ResumeReviewService resumeReviewService;
    private final EventApplicantHistoryService eventApplicantHistoryService;
    private final FileStorageService fileStorageService;
    private final MessageHistoryService messageHistoryService;

    @Async("eventAsyncExecutor")
    public void processMessage(EventApplicantCreateMessageTemplate dto, MessageHistoryReqDto messageHistoryReqDto) {
        MockMultipartFile file = new MockMultipartFile("file", "mockFile.txt", "text/plain", dto.getFile());
        FileMetadata metadata = fileStorageService.upload(file);
        Applicant applicant = dto.toEntity(metadata.getFilePath());
        applicantService.save(applicant);
        resumeReviewService.initialize(applicant.getId());
        eventApplicantHistoryService.save(new EventApplicantHistory(dto.getEventId(), applicant.getId()));
        messageHistoryReqDto.setMessage(dto.toString());
        messageHistoryService.save(messageHistoryReqDto, MessageStatus.SUCCESS, null);
    }
}
