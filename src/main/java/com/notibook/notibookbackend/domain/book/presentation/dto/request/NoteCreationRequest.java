package com.notibook.notibookbackend.domain.book.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class NoteCreationRequest {
    @NotNull
    private Integer page;

    @NotNull
    @NotEmpty
    private String content;
}
