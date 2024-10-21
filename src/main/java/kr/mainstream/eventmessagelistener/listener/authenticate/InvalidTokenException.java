package kr.mainstream.eventmessagelistener.listener.authenticate;

public class InvalidTokenException extends IllegalAccessException {

    public InvalidTokenException(String msg) {
        super(msg);
    }
}
