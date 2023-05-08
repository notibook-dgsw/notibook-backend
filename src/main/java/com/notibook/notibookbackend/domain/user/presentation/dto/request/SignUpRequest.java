package com.notibook.notibookbackend.domain.user.presentation.dto.request;

import com.notibook.notibookbackend.domain.user.entity.UserEntity;
import com.notibook.notibookbackend.global.presentation.EntityConverterWithDependency;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class SignUpRequest implements EntityConverterWithDependency<UserEntity, PasswordEncoder> {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String loginId;

    @NotNull
    @NotEmpty
    private String password;

    @Override
    public UserEntity convertToEntity(PasswordEncoder encoder) {
        return UserEntity.builder()
                .loginId(loginId)
                .name(name)
                .password(encoder.encode(password))
                .build();
    }
}
