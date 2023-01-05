package com.example.smart_waiting.domain.user.service;

import com.example.smart_waiting.domain.user.dto.UserInput;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.user.repository.UserRepository;
import com.example.smart_waiting.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.smart_waiting.exception.UserErrorCode.EMAIL_ALREADY_EXIST;
import static com.example.smart_waiting.exception.UserErrorCode.PHONE_ALREADY_EXIST;

@RequiredArgsConstructor
@Service
public class UserWriteService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserInput userInput) {

        if(userRepository.existsByEmail(userInput.getEmail())){
            throw new UserException(EMAIL_ALREADY_EXIST);}

        if(userRepository.existsByPhone(userInput.getPhone())){
            throw new UserException(PHONE_ALREADY_EXIST);}

        String encryptPassword = passwordEncoder.encode(userInput.getPassword());

        User user = User.builder()
                .name(userInput.getName())
                .email(userInput.getEmail())
                .password(encryptPassword)
                .phone(userInput.getPhone())
                .build();

        userRepository.save(user);

        //인증 메일 발송 처리 -> 카프카
    }

}
