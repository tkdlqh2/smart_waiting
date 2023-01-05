package com.example.smart_waiting.domain.user.service;

import com.example.smart_waiting.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserReadService {

    private final UserRepository userRepository;

    public boolean existEmail(String email) {return userRepository.existsByEmail(email);}
}
