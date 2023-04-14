package com.notibook.notibookbackend.global.presentation;

public interface EntityConverterWithDependency<T, D> {
    T convertToEntity(D dependency);
}
