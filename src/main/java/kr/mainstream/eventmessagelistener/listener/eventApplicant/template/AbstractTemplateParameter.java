package kr.mainstream.eventmessagelistener.listener.eventApplicant.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.lang.reflect.Field;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractTemplateParameter {
    public void validateIsNull() {
        for (Field f : this.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (null == f.get(this)) throw new IllegalArgumentException("missing parameter " + f.getName());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
