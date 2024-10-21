package kr.mainstream.eventmessagelistener.message.history;

import kr.mainstream.eventmessagelistener.message.MessageType;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class MessageHistoryReqDto {
    private MessageType type;
    private String message;
    private LocalDateTime createdAt;

    public void init(LocalDateTime currentDateTime, MessageType type) {
        this.createdAt = currentDateTime;
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
