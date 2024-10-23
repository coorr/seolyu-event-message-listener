package kr.mainstream.eventmessagelistener.domain.applicant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantService {
    private final ApplicantRepository applicantRepository;

    @Transactional
    public Applicant save(Applicant applicant) {
        return applicantRepository.save(applicant);
    }

    public List<Applicant> getList() {
        return applicantRepository.findAll();
    }
}
