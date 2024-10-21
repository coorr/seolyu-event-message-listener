package kr.mainstream.eventmessagelistener.listener.dto;

import org.springframework.util.Assert;

public interface ConsumeDto {
    String getToken();

    default void validateDefault() {
        Assert.hasLength(this.getToken(), "token is empty");
    }
}
