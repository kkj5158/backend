package com.example.miniproject.service;

import com.example.miniproject.dto.LoginRequestDto;
import com.example.miniproject.entity.User;
import com.example.miniproject.config.jwt.JwtUtil;
import com.example.miniproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String userId = loginRequestDto.getUserId();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("dd"));// 예외처리 해주기

//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new IllegalStateException("dd");
//        }
        if (!password.equals(user.getPassword())) {
            throw new IllegalArgumentException("dd");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUserId()));
    }
}
