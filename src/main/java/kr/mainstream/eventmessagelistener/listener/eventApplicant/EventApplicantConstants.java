package kr.mainstream.eventmessagelistener.listener.eventApplicant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventApplicantConstants {
    public static final String EXCHANGE_NAME = "seolyu-event.exchange";
    public static final String QUEUE_NAME = "seolyu-event.queue";
    public static final String ROUTING_KEY = "seolyu-event.key";
}
