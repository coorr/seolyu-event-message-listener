package kr.mainstream.eventmessagelistener.infrastructure.file.development;

import kr.mainstream.eventmessagelistener.common.exception.file.EmptyFileException;
import kr.mainstream.eventmessagelistener.common.exception.file.FileUploadException;
import kr.mainstream.eventmessagelistener.infrastructure.file.FileMetadata;
import kr.mainstream.eventmessagelistener.infrastructure.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocalFileStorageService implements FileStorageService {
    public static final String FILE_DIRECTORY = "/Users/gimjinseong/Downloads/resume-pdf/";

    @Override
    public FileMetadata upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new EmptyFileException();
        }
        FileMetadata metadata = new FileMetadata(file);
        Path filePath = Paths.get(FILE_DIRECTORY + metadata.getStoredFilename());

        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            log.error("file upload failed message : {} " , e.getMessage());
            throw new FileUploadException();
        }

        metadata.setFilePath(filePath.toString());
        return metadata;
    }
}
