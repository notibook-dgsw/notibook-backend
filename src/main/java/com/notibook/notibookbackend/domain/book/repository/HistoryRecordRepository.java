package com.notibook.notibookbackend.domain.book.repository;

import com.notibook.notibookbackend.domain.book.entity.HistoryEntity;
import com.notibook.notibookbackend.domain.book.entity.key.UserBookRecordBinder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRecordRepository extends CrudRepository<HistoryEntity, UserBookRecordBinder> {

}
