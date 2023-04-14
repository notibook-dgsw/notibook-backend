package com.notibook.notibookbackend.domain.book.repository;

import com.notibook.notibookbackend.domain.book.entity.NoteEntity;
import com.notibook.notibookbackend.domain.book.entity.key.UserBookRecordBinder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends CrudRepository<NoteEntity, UserBookRecordBinder> {

}
