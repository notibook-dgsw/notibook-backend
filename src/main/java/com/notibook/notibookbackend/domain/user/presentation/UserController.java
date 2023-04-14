package com.notibook.notibookbackend.domain.user.presentation;

import com.notibook.notibookbackend.domain.user.presentation.dto.response.UserBookRecommendResponse;
import com.notibook.notibookbackend.domain.user.presentation.dto.response.UserPageResponse;
import com.notibook.notibookbackend.domain.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {
    private final UserService userService;

    @ApiOperation("사용자 정보")
    @GetMapping
    public UserPageResponse getUserPage(Model model) {
        return userService.userPage();
    }

    @ApiOperation("사용자 추천 도서 조회")
    @GetMapping("/recommend")
    public UserBookRecommendResponse getRecommend() {
        return userService.getRecommend();
    }

}