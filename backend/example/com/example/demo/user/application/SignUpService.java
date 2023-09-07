package com.example.demo.user.application;

import com.example.demo.user.adapter.out.persistence.UserRepository;
import com.example.demo.user.adapter.out.s3.SaveProfileImageAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final SaveProfileImageAdapter saveProfileImageAdapter;
    private final UserRepository userRepository;

    public boolean signUp(String nickname, MultipartFile file) {
        String url = saveProfileImageAdapter.save(file);
        User user = new User(nickname, url);
        userRepository.save(user);
    }

}
