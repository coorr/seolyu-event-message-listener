package kr.mainstream.eventmessagelistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class KeepAliveController {
    @GetMapping("/health")
    public String ping() {
        log.info("health check");
        log.info("now : {}", LocalDateTime.now());
        return "ok";
    }
}
