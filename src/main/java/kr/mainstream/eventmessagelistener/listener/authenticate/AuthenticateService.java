package kr.mainstream.eventmessagelistener.listener.authenticate;

import kr.mainstream.eventmessagelistener.listener.dto.AbstractEventConsumeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateService {
    @Value("${listener-key}")
    private String authKey;

    public void authenticate(AbstractEventConsumeDto dto) throws InvalidTokenException {
        if (!this.authKey.equals(dto.getToken())) {
            throw new InvalidTokenException("invalid token");
        }
    }
}
