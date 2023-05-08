package com.notibook.notibookbackend.domain.book.presentation.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class NoteEditRequest {
    @NotNull
    @NotEmpty
    private String content;
}
