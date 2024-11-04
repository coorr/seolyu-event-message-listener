package kr.mainstream.eventmessagelistener.infrastructure.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Setter
@ToString
public class FilePayload {
    private byte[] content;
    private String originalFilename;
    private String contentType;
    private long size;
}
