package com.notibook.notibookbackend.domain.book.entity;

import com.notibook.notibookbackend.domain.book.entity.key.UserBookRecordBinder;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "note")
public class NoteEntity {
    @Setter
    @EmbeddedId
    private UserBookRecordBinder id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Lob
    @Setter
    @Column(nullable = false)
    private String content;
}
