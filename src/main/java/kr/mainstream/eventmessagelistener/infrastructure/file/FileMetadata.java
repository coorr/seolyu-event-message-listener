package kr.mainstream.eventmessagelistener.infrastructure.file;


import kr.mainstream.eventmessagelistener.common.exception.file.NotSupportedFileTypeException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
public class FileMetadata {
    private final String originalFilename;
    private final String storedFilename;
    private final long size;
    private final String contentType;
    private String filePath;

    public FileMetadata(MultipartFile file) {
        this.originalFilename = file.getOriginalFilename();
        this.storedFilename = createStoredFilename(file.getOriginalFilename());
        this.size = file.getSize();
        this.contentType = file.getContentType();
    }

    public FileMetadata(MultipartFile file, String filePath) {
        this.originalFilename = file.getOriginalFilename();
        this.storedFilename = createStoredFilename(file.getOriginalFilename());
        this.size = file.getSize();
        this.contentType = file.getContentType();
        this.filePath = filePath;
    }

    private String createStoredFilename(String originalFilename) {
        return UUID.randomUUID().toString() + getFileExtension(originalFilename);
    }

    private String getFileExtension(String filename) {
        try {
            return filename.substring(filename.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new NotSupportedFileTypeException();
        }
    }
}
