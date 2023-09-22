package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kangkimleekojangcho.akgimi.global.exception.ServerErrorException;
import com.kangkimleekojangcho.akgimi.global.exception.ServerErrorExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandUserProfileImagePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Component
public class UserProfileImageAdapter implements CommandUserProfileImagePort {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;

    @Override
    public String save(Long userId, MultipartFile file) {
        try {
            String fileName = String.valueOf(userId);
            String[] strings = requireNonNull(file.getOriginalFilename()).split("\\.");
            String fileType = strings[strings.length - 1];
            fileName = "images/profileimage" + fileName + "." + fileType + LocalDateTime.now();
            String fileUrl = "https://" + bucket + ".s3." +
                    region + ".amazonaws.com/" + fileName;
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), objectMetadata);
            return fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerErrorException(ServerErrorExceptionCode.NETWORK_ERROR);
        }
    }
}
