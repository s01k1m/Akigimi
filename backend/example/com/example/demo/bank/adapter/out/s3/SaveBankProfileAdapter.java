package com.example.demo.bank.adapter.out.s3;

import com.example.demo.library.s3.S3Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class SaveBankProfileAdapter {
    private final S3Writer s3Writer;
    public void save(String name, MultipartFile file) {
        String path = "bankProfile/" + name;
        s3Writer.write(path,file);
    }
}