package com.example.smart_waiting.domain.user.repository;

import com.example.smart_waiting.domain.user.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth,Long> {
    Object findByUserId(Long userId);
}
