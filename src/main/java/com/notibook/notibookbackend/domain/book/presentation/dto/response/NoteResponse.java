package com.notibook.notibookbackend.domain.book.presentation.dto.response;

import com.notibook.notibookbackend.domain.book.entity.NoteEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
public class NoteResponse extends HistoryResponse {
    private String content;
    private LocalDate modifiedAt;

    public static NoteResponse fromEntity(NoteEntity entity) {
        return NoteResponse.builder()
                .page(entity.getId().getPage())
                .createdAt(entity.getCreatedAt().toLocalDate())
                .modifiedAt(entity.getModifiedAt().toLocalDate())
                .content(entity.getContent())
                .build();
    }
}
