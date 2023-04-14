package com.notibook.notibookbackend.domain.book.entity;

import com.notibook.notibookbackend.domain.user.entity.UserEntity;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "book_binding", uniqueConstraints = {
        @UniqueConstraint(name = "unique_constraint", columnNames = { "isbn", "user_id" })
})
public class UserBookEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "isbn", referencedColumnName = "isbn")
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany(orphanRemoval = true, mappedBy = "id.binding")
    private Set<HistoryEntity> historyRecords;

    public void addRecord(HistoryEntity entity) {
        historyRecords.add(entity);
    }

    @OneToMany(orphanRemoval = true, mappedBy = "id.binding")
    private Set<NoteEntity> notes;

    public void addNote(NoteEntity entity) {
        notes.add(entity);
    }

    public Integer getCurrentPage() {
        return getHistoryRecords().stream()
                .max(Comparator.comparing(HistoryEntity::getCreatedAt))
                .map(it -> it.getId().getPage())
                .orElse(0);
    }

    public Integer getProgress() {
        return getBook().getPageCount() != null ?
                (int)(getCurrentPage() / (double)getBook().getPageCount() * 100) : null;
    }
}
