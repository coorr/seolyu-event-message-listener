package kr.mainstream.eventmessagelistener.message.template;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TemplateType {
    EVENT_APPLICANT_CREATED(new EventApplicantCreateMessageTemplate());

    private final AbstractMessageTemplate template;

    public Class<? extends AbstractMessageTemplate> getTemplateClass() {
        return this.template.getClass();
    }
}
