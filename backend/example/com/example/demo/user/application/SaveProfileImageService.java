package com.example.demo.user.application;

import com.example.demo.user.adapter.out.s3.SaveProfileImageAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SaveProfileImageService {
    private final SaveProfileImageAdapter saveProfileImageAdapter;

    public void save(MultipartFile file) {
        saveProfileImageAdapter.save(file);
    }
}
