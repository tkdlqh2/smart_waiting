package com.example.smart_waiting.domain.user.service;

import com.example.smart_waiting.domain.user.dto.UserInput;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.user.repository.UserRepository;
import com.example.smart_waiting.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.smart_waiting.domain.user.type.UserStatus.UNAPPROVED;
import static com.example.smart_waiting.exception.UserErrorCode.*;

@RequiredArgsConstructor
@Service
public class UserWriteService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Long EXPIRE_DAY = 3L;

    @Transactional
    public void createUser(UserInput userInput) {
        if(userRepository.existsByEmail(userInput.getEmail())){
            throw new UserException(EMAIL_ALREADY_EXIST);}

        if(userRepository.existsByPhone(userInput.getPhone())){
            throw new UserException(PHONE_ALREADY_EXIST);}

        String encryptPassword = passwordEncoder.encode(userInput.getPassword());

        userRepository.save(User.builder()
                .name(userInput.getName())
                .email(userInput.getEmail())
                .password(encryptPassword)
                .phone(userInput.getPhone())
                .userStatus(UNAPPROVED)
                .authKey(UUID.randomUUID().toString())
                .expireDateTime(LocalDateTime.now().plusDays(EXPIRE_DAY))
                .build());
        //인증 메일 발송 처리 -> 카프카
    }

    @Transactional(noRollbackFor = UserException.class)
    public void emailAuth(Long userId, String authKey) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new UserException(USER_NOT_FOUND));

        if(user.getExpireDateTime().isBefore(LocalDateTime.now())){
            userRepository.delete(user);
            throw new UserException(CODE_ALREADY_EXPIRED);
        } else if (!user.getAuthKey().equals(authKey)) {
            throw new UserException(CODE_MISMATCH);
        }

        user.approve();
        userRepository.save(user);
    }
}
