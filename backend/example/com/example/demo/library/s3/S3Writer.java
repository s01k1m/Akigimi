package com.example.demo.library.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class S3Writer {
    private final String s3bucket;
    private final String s3password;

    public void write(String path, MultipartFile file) {
        /// ...
    }
}
