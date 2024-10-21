package kr.mainstream.eventmessagelistener.message.history;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageHistoryService {
    private final MessageHistoryRepository messageHistoryRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MessageHistory save(MessageHistoryReqDto dto, MessageStatus status, String errorMessage) {
        MessageHistory messageHistory = new MessageHistory(
                dto.getType(),
                status,
                dto.getMessage(),
                errorMessage,
                dto.getCreatedAt(),
                LocalDateTime.now()
        );
        return messageHistoryRepository.save(messageHistory);
    }
}
