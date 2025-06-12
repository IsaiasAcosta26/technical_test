package com.oriontek.prueba.tecnica.application.processor;

import java.util.List;

public interface BaseProcessor<T> {
        T create(T entity);
        T getById(Long id);
        List<T> getAll();
        T update(Long id, T entity);
        void delete(Long id);
}