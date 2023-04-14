package com.notibook.notibookbackend.domain.book.entity;

import com.notibook.notibookbackend.domain.book.entity.key.UserBookRecordBinder;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "history")
public class HistoryEntity {
    @Setter
    @EmbeddedId
    private UserBookRecordBinder id;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
