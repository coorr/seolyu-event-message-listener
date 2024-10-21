package kr.mainstream.eventmessagelistener.infrastructure.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    FileMetadata upload(MultipartFile file);
}
