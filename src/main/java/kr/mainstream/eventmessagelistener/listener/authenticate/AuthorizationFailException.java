package kr.mainstream.eventmessagelistener.listener.authenticate;

public class AuthorizationFailException extends IllegalAccessException {
    public AuthorizationFailException(String msg) {
        super(msg);
    }
}
