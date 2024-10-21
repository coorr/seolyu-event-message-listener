package kr.mainstream.eventmessagelistener.common.exception.file;

public class EmptyFileException extends IllegalArgumentException {
    public EmptyFileException() {
        super("common.text.file.error.empty"); // 파일을 선택해주세요.
    }
}
