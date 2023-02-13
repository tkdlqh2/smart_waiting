package com.example.smart_waiting.domain.user.entity;

import com.example.smart_waiting.domain.base.BaseEntity;
import com.example.smart_waiting.domain.user.type.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.smart_waiting.domain.user.type.UserStatus.APPROVED;
import static com.example.smart_waiting.domain.user.type.UserStatus.STOPPED;

@Getter
@SuperBuilder
@Entity
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false,unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "phone", nullable = false,unique = true)
    private String phone;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    private String authKey;
    private LocalDateTime expireDateTime;
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public User(){
        super(LocalDateTime.now(),null);
    }

    public void approve(){
        this.userStatus = APPROVED;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.userStatus.equals(STOPPED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.userStatus.equals(APPROVED);
    }
}
