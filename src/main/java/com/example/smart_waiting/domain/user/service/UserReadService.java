package com.example.smart_waiting.domain.user.service;

import com.example.smart_waiting.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserReadService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean existEmail(String email) {return userRepository.existsByEmail(email);}

    @Transactional(readOnly = true)
    public boolean existPhone (String phone) {return userRepository.existsByPhone(phone);}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username+"을 찾을 수 없습니다."));
    }
}
