package com.notibook.notibookbackend.domain.user.presentation;

import com.notibook.notibookbackend.domain.user.presentation.dto.request.SignInRequest;
import com.notibook.notibookbackend.domain.user.presentation.dto.request.SignUpRequest;
import com.notibook.notibookbackend.domain.user.presentation.dto.response.SignInResponse;
import com.notibook.notibookbackend.domain.user.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/auth")
@Controller
public class AuthController {
    private final AuthService authService;

    @ApiOperation("로그인")
    @PostMapping("/sign-in")
    public SignInResponse signIn(@RequestBody @Validated SignInRequest request) {
        return authService.signIn(request);
    }

    @ApiOperation("회원가입")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public void signUp(@RequestBody @Validated SignUpRequest request) {
        authService.signUp(request);
    }
}