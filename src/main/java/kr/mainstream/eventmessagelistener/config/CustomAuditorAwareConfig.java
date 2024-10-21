package kr.mainstream.eventmessagelistener.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class CustomAuditorAwareConfig implements AuditorAware<Long> {

    private final HttpServletRequest request;

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.ofNullable(1001L);
    }
}
