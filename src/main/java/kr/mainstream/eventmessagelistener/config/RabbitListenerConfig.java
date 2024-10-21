package kr.mainstream.eventmessagelistener.config;

import kr.mainstream.eventmessagelistener.listener.eventApplicant.EventApplicantMessageListener;
import kr.mainstream.eventmessagelistener.listener.eventApplicant.EventApplicantConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@EnableRabbit
@RequiredArgsConstructor
@Configuration
@Slf4j
public class RabbitListenerConfig {
    private final EventApplicantMessageListener eventApplicantMessageListener;

    @Bean
    @Description("이벤트 신청")
    public SimpleMessageListenerContainer eventApplicationMessageListenerContainer(ConnectionFactory cf) {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(cf);
        listenerContainer.setQueueNames(EventApplicantConstants.QUEUE_NAME);
        listenerContainer.setMessageListener(eventApplicantMessageListener);
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        log.info("now consumes queue named {}", listenerContainer.getQueueNames()[0]);
        return listenerContainer;
    }

    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate) {
        return new AsyncRabbitTemplate(rabbitTemplate);
    }
}
