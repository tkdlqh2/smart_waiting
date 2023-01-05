package com.example.smart_waiting.domain.user.service;

import com.example.smart_waiting.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserReadService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean existEmail(String email) {return userRepository.existsByEmail(email);}

    @Transactional(readOnly = true)
    public boolean existPhone (String phone) {return userRepository.existsByPhone(phone);}
}
