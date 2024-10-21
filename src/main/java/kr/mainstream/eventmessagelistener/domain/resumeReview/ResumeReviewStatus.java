package kr.mainstream.eventmessagelistener.domain.resumeReview;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResumeReviewStatus {
    READY("대기"),
    COMPLETED("완료"),
    CANCEL("취소");

    private final String label;
}
