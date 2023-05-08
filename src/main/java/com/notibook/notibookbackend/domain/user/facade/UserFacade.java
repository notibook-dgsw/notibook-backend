package com.notibook.notibookbackend.domain.user.facade;

import com.notibook.notibookbackend.domain.user.entity.UserEntity;
import com.notibook.notibookbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Component
public class UserFacade {
    private final UserRepository userRepository;

    public Optional<UserEntity> getCurrentUser() {
        try {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
            if(!token.isAuthenticated())
                return Optional.empty();

            UserEntity user = (UserEntity) token.getPrincipal();
            return userRepository.findById(user.getId());
        } catch (NullPointerException exception) {
            return Optional.empty();
        }
    }
}
