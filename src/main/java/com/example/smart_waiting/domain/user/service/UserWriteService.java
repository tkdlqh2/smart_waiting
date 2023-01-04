package com.example.smart_waiting.domain.user.service;

import com.example.smart_waiting.domain.user.dto.UserInput;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserWriteService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserInput userInput) {

        String encryptPassword = passwordEncoder.encode(userInput.getPassword());

        User user = User.builder()
                .name(userInput.getName())
                .email(userInput.getEmail())
                .password(encryptPassword)
                .phone(userInput.getPhone())
                .build();

        userRepository.save(user);
    }

}
