package com.example.smart_waiting.domain.user.service;

import com.example.smart_waiting.domain.user.dto.UserLogInInput;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.user.repository.UserRepository;
import com.example.smart_waiting.exception.UserException;
import com.example.smart_waiting.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.smart_waiting.exception.error_code.UserErrorCode.PASSWORD_NOT_MATCH;
import static com.example.smart_waiting.exception.error_code.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserReadService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public boolean existEmail(String email) {return userRepository.existsByEmail(email);}

    @Transactional(readOnly = true)
    public boolean existPhone (String phone) {return userRepository.existsByPhone(phone);}



    @Transactional(readOnly = true)
    public String signIn(UserLogInInput parameter) throws UserException {
        User user = userRepository.findByEmail(parameter.getEmail())
                .orElseThrow(()-> new UserException(USER_NOT_FOUND));

        if(!passwordEncoder.matches(parameter.getPassword(), user.getPassword())){
            throw new UserException(PASSWORD_NOT_MATCH);
        }

        return jwtTokenProvider.createToken(String.valueOf(user.getUsername()),user.getRoles());
    }
}
