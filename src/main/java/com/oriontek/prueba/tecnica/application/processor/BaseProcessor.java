package com.oriontek.prueba.tecnica.application.processor;

import com.oriontek.prueba.tecnica.application.mapper.BaseMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseProcessor<T, D> {

        protected final JpaRepository<T, Long> repository;
        protected final BaseMapper<T, D> mapper;

        protected BaseProcessor(JpaRepository<T, Long> repository, BaseMapper<T, D> mapper) {
                this.repository = repository;
                this.mapper = mapper;
        }

        public D create(D dto) {
                T entity = mapper.toEntity(dto);
                return mapper.toDto(repository.save(entity));
        }

        public D getById(Long id) {
                T entity = repository.findById(id).orElseThrow(() -> new RuntimeException("No encontrado con ID: " + id));
                return mapper.toDto(entity);
        }

        public List<D> getAll() {
                return repository.findAll().stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList());
        }

        public D update(Long id, D dto) {
                Optional<T> optional = repository.findById(id);
                if (optional.isEmpty()) {
                        throw new RuntimeException("No encontrado con ID: " + id);
                }
                T entity = mapper.toEntity(dto);
                return mapper.toDto(repository.save(entity));
        }

        public void delete(Long id) {
                repository.deleteById(id);
        }
}
