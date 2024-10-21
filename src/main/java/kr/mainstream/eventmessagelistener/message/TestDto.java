package kr.mainstream.eventmessagelistener.message;

import kr.mainstream.eventmessagelistener.message.template.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
    private TemplateType templateType;
}
