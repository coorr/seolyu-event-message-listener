package kr.mainstream.eventmessagelistener.listener.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Slf4j
public abstract class AbstractEventConsumeDto implements ConsumeDto {
    private String token;
    private Boolean drill = Boolean.FALSE;

    public abstract void validate();
}
