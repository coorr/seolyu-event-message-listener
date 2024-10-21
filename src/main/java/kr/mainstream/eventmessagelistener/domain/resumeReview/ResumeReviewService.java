package kr.mainstream.eventmessagelistener.domain.resumeReview;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResumeReviewService {
    private final ResumeReviewRepository resumeReviewRepository;

    @Transactional
    public ResumeReview initialize(Long applicantId) {
        ResumeReview resumeReview = new ResumeReview(applicantId);
        resumeReviewRepository.save(resumeReview);
        return resumeReview;
    }
}
