package kr.mainstream.eventmessagelistener.domain.event.applicationIssue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventApplicantHistoryService {
    private final EventApplicantHistoryRepository eventApplicantHistoryRepository;

    @Transactional
    public void save(EventApplicantHistory eventApplicantHistory) {
        eventApplicantHistoryRepository.save(eventApplicantHistory);
    }
}
