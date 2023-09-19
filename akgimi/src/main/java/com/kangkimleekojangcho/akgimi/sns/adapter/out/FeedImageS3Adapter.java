package com.kangkimleekojangcho.akgimi.sns.adapter.out;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandFeedImagePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class FeedImageS3Adapter implements CommandFeedImagePort {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;

    @Override
    public Optional<String> save(MultipartFile file, long userId) {
        try {
            String fileName = String.valueOf(userId);

            String[] strings = requireNonNull(file.getOriginalFilename()).split("\\.");
            String fileType = strings[strings.length - 1];
            fileName = "images/feed" + fileName + "." + fileType + LocalDateTime.now();
            String fileUrl = "https://" + bucket + ".s3." +
                    region + ".amazonaws.com/" + fileName;
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), objectMetadata);
            return Optional.of(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}