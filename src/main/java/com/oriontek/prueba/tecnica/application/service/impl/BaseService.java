package com.oriontek.prueba.tecnica.application.service.impl;

import com.oriontek.prueba.tecnica.application.mapper.BaseMapper;
import com.oriontek.prueba.tecnica.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseService<T, D> {

    protected final JpaRepository<T, Long> repository;
    protected final BaseMapper<T, D> mapper;

    protected BaseService(JpaRepository<T, Long> repository, BaseMapper<T, D> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public D create(D dto) {
        T entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public D getById(Long id) {
        T entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found with ID: " + id));
        return mapper.toDto(entity);
    }

    public List<D> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public abstract D update(Long id, D dto);

    public void delete(Long id) {
        T entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found with ID: " + id));
        repository.delete(entity);
    }
}
