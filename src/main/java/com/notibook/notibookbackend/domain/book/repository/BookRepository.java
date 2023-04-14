package com.notibook.notibookbackend.domain.book.repository;

import com.notibook.notibookbackend.domain.book.entity.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, String> {

}
