package com.oriontek.prueba.tecnica.application.mapper;


public interface BaseMapper<T, D> {
    T toEntity(D dto);
    D toDto(T entity);
}