package com.swig.manda.model;

import com.swig.manda.dto.MemberDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
    private String provider;
    private String providerId;

    @CreationTimestamp
    private LocalDateTime regTime;
    @CreationTimestamp
    private LocalDateTime loginTime;

    @Builder
    public Member(String username, String password, String role, LocalDateTime regTime,String provider,String providerId, LocalDateTime loginTime) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.regTime = regTime;
        this.loginTime = loginTime;
        this.providerId= providerId;
        this.provider=provider;
    }
}