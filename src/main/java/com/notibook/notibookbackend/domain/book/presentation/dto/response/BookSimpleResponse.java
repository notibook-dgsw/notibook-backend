package com.notibook.notibookbackend.domain.book.presentation.dto.response;

import com.notibook.notibookbackend.domain.book.entity.HistoryEntity;
import com.notibook.notibookbackend.domain.book.entity.UserBookEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Comparator;

@Getter
@SuperBuilder
public class BookSimpleResponse {
    private String isbn;
    private String title;
    private String author;
    private LocalDate startedAt;
    private Integer allPages;
    private Integer currentPage;
    private Integer progress;

    public static BookSimpleResponse fromEntity(UserBookEntity entity) {
        return BookSimpleResponse.builder()
                .isbn(entity.getBook().getIsbn())
                .allPages(entity.getBook().getPageCount())
                .currentPage(entity.getCurrentPage())
                .startedAt(entity.getHistoryRecords().stream()
                        .min(Comparator.comparing(HistoryEntity::getCreatedAt))
                        .map(it -> it.getCreatedAt().toLocalDate()).orElseThrow())
                .progress(entity.getProgress())
                .title(entity.getBook().getTitle())
                .author(entity.getBook().getAuthor())
                .build();
    }
}
