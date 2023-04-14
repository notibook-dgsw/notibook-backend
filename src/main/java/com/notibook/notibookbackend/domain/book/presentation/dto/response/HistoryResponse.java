package com.notibook.notibookbackend.domain.book.presentation.dto.response;

import com.notibook.notibookbackend.domain.book.entity.HistoryEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
public class HistoryResponse {
    private LocalDate createdAt;
    private int page;

    public static HistoryResponse fromEntity(HistoryEntity entity) {
        return HistoryResponse.builder()
                .page(entity.getId().getPage())
                .createdAt(entity.getCreatedAt().toLocalDate())
                .build();
    }
}
