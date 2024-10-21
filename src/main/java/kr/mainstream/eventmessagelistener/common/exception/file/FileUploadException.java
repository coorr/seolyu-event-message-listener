package kr.mainstream.eventmessagelistener.common.exception.file;


public class FileUploadException extends IllegalStateException {
    public FileUploadException() {
        super("파일 업로드 실패했습니다.");
    }
}
