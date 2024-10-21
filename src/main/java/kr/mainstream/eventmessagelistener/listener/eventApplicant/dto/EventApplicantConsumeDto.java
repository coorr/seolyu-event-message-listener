package kr.mainstream.eventmessagelistener.listener.eventApplicant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.mainstream.eventmessagelistener.message.template.AbstractMessageTemplate;
import kr.mainstream.eventmessagelistener.message.template.TemplateType;
import kr.mainstream.eventmessagelistener.listener.dto.AbstractEventConsumeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@NoArgsConstructor
@Slf4j
public class EventApplicantConsumeDto<T extends AbstractMessageTemplate> extends AbstractEventConsumeDto {
    private TemplateType templateType;
    private T message;

    @Override
    public void validate() {
        validateDefault();
        Assert.notNull(templateType, "templateType must not be null");
        message.validateIsNull();
    }
}
