package com.notibook.notibookbackend.domain.book.presentation.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HistoryCreationRequest {
    @NotNull
    private Integer page;
}
