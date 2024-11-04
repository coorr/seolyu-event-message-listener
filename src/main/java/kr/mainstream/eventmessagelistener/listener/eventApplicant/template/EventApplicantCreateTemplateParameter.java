package kr.mainstream.eventmessagelistener.listener.eventApplicant.template;

import kr.mainstream.eventmessagelistener.domain.applicant.Applicant;
import kr.mainstream.eventmessagelistener.domain.applicant.JobPosition;
import kr.mainstream.eventmessagelistener.infrastructure.file.FilePayload;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString(exclude = "file")
public class EventApplicantCreateTemplateParameter extends AbstractTemplateParameter {
    private String name;
    private String email;
    private JobPosition position;
    private String resumeUrl;
    private String requestDetails;
    private Long eventId;
    private FilePayload file;

    public Applicant toEntity(String resumeFile) {
        return new Applicant(name, email, position, resumeUrl, resumeFile, requestDetails);
    }

    @Override
    public void validateIsNull() {
        return;
    }
}
