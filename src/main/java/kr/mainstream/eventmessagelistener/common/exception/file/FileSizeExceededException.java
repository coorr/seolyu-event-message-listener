package kr.mainstream.eventmessagelistener.common.exception.file;

public class FileSizeExceededException extends IllegalArgumentException {
    public FileSizeExceededException() {
        super("파일 크기가 20MB를 초과합니다.");
    }
}
