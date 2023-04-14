package com.notibook.notibookbackend.domain.book.repository;

import com.notibook.notibookbackend.domain.book.entity.BookEntity;
import com.notibook.notibookbackend.domain.book.entity.UserBookEntity;
import com.notibook.notibookbackend.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookBindingRepository extends CrudRepository<UserBookEntity, Long> {

    List<UserBookEntity> findAllByUser(UserEntity user);

    @Query("SELECT b FROM UserBookEntity b WHERE b.book=:book AND b.user=:user")
    Optional<UserBookEntity> findByBookAndUser(BookEntity book, UserEntity user);
}
