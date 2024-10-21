package kr.mainstream.eventmessagelistener.listener.aspect;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class ListenerHandlingAspect {

    @Before("execution(* kr.mainstream.eventmessagelistener.listener..*Listener.onMessage(..))")
    public void logBeforeConsume(JoinPoint joinPoint) {
        final Object[] args = joinPoint.getArgs();
        try {
            Message message = (Message) args[0];
            Channel channel = (Channel) args[1];
            log.info("message consuming: {}", new String(message.getBody()));
            log.info("message consuming channel: {}", channel);
        } catch (Exception ignored) {}
    }

    @After("execution(* kr.mainstream.eventmessagelistener.listener..*Listener.onMessage(..))")
    public void logAfterConsume() {
        log.info("message handler terminated.");
    }
}
