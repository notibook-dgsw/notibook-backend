package com.notibook.notibookbackend.domain.book.entity.key;

import com.notibook.notibookbackend.domain.book.entity.UserBookEntity;
import javax.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserBookRecordBinder implements Serializable {
    @JoinColumn(referencedColumnName = "id")
    @ManyToOne
    private UserBookEntity binding;

    @Column(nullable = false)
    private Integer page;
}
