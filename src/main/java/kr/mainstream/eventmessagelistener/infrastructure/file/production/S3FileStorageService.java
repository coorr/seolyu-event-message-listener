package kr.mainstream.eventmessagelistener.infrastructure.file.production;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import kr.mainstream.eventmessagelistener.common.exception.file.EmptyFileException;
import kr.mainstream.eventmessagelistener.infrastructure.file.FileMetadata;
import kr.mainstream.eventmessagelistener.infrastructure.file.FileStorageService;
import kr.mainstream.eventmessagelistener.common.exception.file.FileUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.Executor;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3FileStorageService implements FileStorageService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.images-base-url}")
    private String imagesBaseUrl;
    private final AmazonS3 amazonS3;
    private final Executor fileTaskExecutor;

    @Override
    public FileMetadata upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new EmptyFileException();
        }

        FileMetadata metadata = new FileMetadata(file);

        Runnable runnable = () -> {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                    PutObjectRequest request = new PutObjectRequest(bucket, "images/" + metadata.getStoredFilename(), inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead);
                    amazonS3.putObject(request);
            } catch (Exception e) {
                log.error("file upload failed message : {} " , e.getMessage());
                throw new FileUploadException();
            }
        };
        fileTaskExecutor.execute(runnable);

        metadata.setFilePath(imagesBaseUrl + metadata.getStoredFilename());
        return metadata;
    }
}
