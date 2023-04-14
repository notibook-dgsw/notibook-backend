package com.notibook.notibookbackend.global.presentation;

public interface DtoConverter<D, T> {
    D fromEntity(T entity);
}
