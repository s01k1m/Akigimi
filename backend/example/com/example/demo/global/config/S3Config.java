package com.example.demo.global.config;

import com.example.demo.library.s3.S3Writer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    @Value("${s3.bucket}")
    String s3bucket;

    @Value("${s3.password}")
    String s3password;

    @Bean
    public S3Writer s3Writer() {
        return new S3Writer(s3bucket, s3password);
    }
}
