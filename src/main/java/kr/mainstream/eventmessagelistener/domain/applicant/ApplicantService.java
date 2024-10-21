package kr.mainstream.eventmessagelistener.domain.applicant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicantService {
    private final ApplicantRepository applicantRepository;

    @Transactional
    public Applicant save(Applicant applicant) {
        return applicantRepository.save(applicant);
    }
}
