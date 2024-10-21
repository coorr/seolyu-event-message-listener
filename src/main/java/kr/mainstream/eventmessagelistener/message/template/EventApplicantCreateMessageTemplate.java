package kr.mainstream.eventmessagelistener.message.template;

import kr.mainstream.eventmessagelistener.domain.applicant.Applicant;
import kr.mainstream.eventmessagelistener.domain.applicant.JobPosition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class EventApplicantCreateMessageTemplate extends AbstractMessageTemplate {
    private String name;
    private String email;
    private JobPosition position;
    private String resumeUrl;
    private String requestDetails;
    private Long eventId;
    private byte[] file;

    public Applicant toEntity(String resumeFile) {
        return new Applicant(name, email, position, resumeUrl, resumeFile, requestDetails);
    }

    @Override
    public void validateIsNull() {
        return;
    }
}
