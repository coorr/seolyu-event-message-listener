package kr.mainstream.eventmessagelistener.domain.event.applicationHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventApplicantHistoryRepository extends JpaRepository<EventApplicantHistory, Long> {
}
