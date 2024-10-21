package kr.mainstream.eventmessagelistener.domain.applicant;

import jakarta.persistence.*;
import kr.mainstream.eventmessagelistener.common.model.BaseEntityCreateUpdateAggregate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "applicant")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applicant extends BaseEntityCreateUpdateAggregate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "email", nullable = false, length = 70)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false, length = 20)
    private JobPosition position;

    @Column(name = "resume_url")
    private String resumeUrl;

    @Column(name = "resume_file", nullable = false)
    private String resumeFile;

    @Column(name = "request_details")
    private String requestDetails;

    public Applicant(String name, String email, JobPosition position, String resumeUrl, String resumeFile, String requestDetails) {
        this.name = name;
        this.email = email;
        this.position = position;
        this.resumeUrl = resumeUrl;
        this.resumeFile = resumeFile;
        this.requestDetails = requestDetails;
    }
}
