package kr.mainstream.eventmessagelistener.listener.eventApplicant.template;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TemplateType {
    EVENT_APPLICANT_CREATED(new EventApplicantCreateTemplateParameter());

    private final AbstractTemplateParameter parameter;

    public Class<? extends AbstractTemplateParameter> getParameterClass() {
        return this.parameter.getClass();
    }
}
