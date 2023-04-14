package com.notibook.notibookbackend.domain.user.repository;

import com.notibook.notibookbackend.domain.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByLoginId(String loginId);
}
