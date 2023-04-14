package com.notibook.notibookbackend.domain.user.service;

import com.notibook.notibookbackend.domain.book.entity.UserBookEntity;
import com.notibook.notibookbackend.domain.book.presentation.dto.response.BookSimpleResponse;
import com.notibook.notibookbackend.domain.book.repository.BookBindingRepository;
import com.notibook.notibookbackend.domain.book.service.BookRecommendService;
import com.notibook.notibookbackend.domain.user.entity.UserEntity;
import com.notibook.notibookbackend.domain.user.exception.UserUnauthorizedException;
import com.notibook.notibookbackend.domain.user.facade.UserFacade;
import com.notibook.notibookbackend.domain.user.presentation.dto.response.UserBookRecommendResponse;
import com.notibook.notibookbackend.domain.user.presentation.dto.response.UserPageResponse;
import com.notibook.notibookbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserFacade userFacade;
    private final UserRepository userRepository;
    private final BookBindingRepository bookBindingRepository;
    private final BookRecommendService bookRecommendService;

    public UserPageResponse userPage() {
        UserEntity user = userFacade.getCurrentUser()
                .orElseThrow(UserUnauthorizedException::new);

        List<BookSimpleResponse> books = bookBindingRepository.findAllByUser(user)
                .stream().map(BookSimpleResponse::fromEntity)
                .collect(Collectors.toList());

        return UserPageResponse.builder()
                .name(user.getName())
                .loginId(user.getLoginId())
                .books(books)
                .build();
    }

    public UserBookRecommendResponse getRecommend() {
        UserEntity user = userFacade.getCurrentUser()
                .orElseThrow(UserUnauthorizedException::new);

        List<String> tokens = Arrays.stream(bookRecommendService.recommend(bookBindingRepository.findAllByUser(user)
                .stream().map(UserBookEntity::getBook)
                .collect(Collectors.toList()))
                .split("\n"))
                .filter(it -> !it.isBlank())
                .map(it -> it.replaceAll("\\d*\\.", ""))
                .collect(Collectors.toList());

        return UserBookRecommendResponse.builder()
                .books(tokens)
                .build();
    }
}
