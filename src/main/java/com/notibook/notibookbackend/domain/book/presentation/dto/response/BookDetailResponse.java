package com.notibook.notibookbackend.domain.book.presentation.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class BookDetailResponse extends BookSimpleResponse {
    private String summary;
    private List<HistoryResponse> history;
    private List<NoteResponse> notes;
}
