package com.notibook.notibookbackend.domain.user.service;

import com.notibook.notibookbackend.domain.user.entity.UserEntity;
import com.notibook.notibookbackend.domain.user.exception.UserNotFoundException;
import com.notibook.notibookbackend.domain.user.exception.UserUnauthorizedException;
import com.notibook.notibookbackend.domain.user.presentation.dto.request.SignInRequest;
import com.notibook.notibookbackend.domain.user.presentation.dto.request.SignUpRequest;
import com.notibook.notibookbackend.domain.user.presentation.dto.response.SignInResponse;
import com.notibook.notibookbackend.domain.user.repository.UserRepository;
import com.notibook.notibookbackend.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signUp(SignUpRequest request) {
        userRepository.save(request.convertToEntity(passwordEncoder));
    }

    public SignInResponse signIn(SignInRequest request) {
        UserEntity user = userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new UserUnauthorizedException();

        return SignInResponse.builder()
                .accessToken(jwtUtil.issueToken(user.getId()))
                .build();
    }
}
