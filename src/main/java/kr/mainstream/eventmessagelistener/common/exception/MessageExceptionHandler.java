package kr.mainstream.eventmessagelistener.common.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageExceptionHandler {
    public static void handle(Message message, String errorMsg, Throwable cause) {
        log.error("unack message: {}", new String(message.getBody()));
        log.error(errorMsg, cause);
    }

    public static void handle(String errorMsg, Throwable cause) {
        log.error(errorMsg, cause);
    }
}
