package com.oriontek.prueba.tecnica.testProcessor;

import com.oriontek.prueba.tecnica.application.mapper.BaseMapper;
import com.oriontek.prueba.tecnica.application.processor.BaseProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaseProcessorTest {

    private JpaRepository<TestEntity, Long> repository;
    private BaseMapper<TestEntity, TestDTO> mapper;
    private BaseProcessor<TestEntity, TestDTO> processor;

    @BeforeEach
    void setUp() {
        repository = mock(JpaRepository.class);
        mapper = mock(BaseMapper.class);
        processor = new BaseProcessor<TestEntity, TestDTO>(repository, mapper) {};
    }

    @Test
    void testCreate() {
        TestDTO dto = new TestDTO(1L, "test");
        TestEntity entity = new TestEntity(1L, "test");

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        TestDTO result = processor.create(dto);

        assertEquals(dto, result);
        verify(repository).save(entity);
    }

    @Test
    void testGetById_found() {
        Long id = 1L;
        TestEntity entity = new TestEntity(id, "found");
        TestDTO dto = new TestDTO(id, "found");

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        TestDTO result = processor.getById(id);

        assertEquals(dto, result);
    }

    @Test
    void testGetById_notFound() {
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> processor.getById(id));
        assertTrue(exception.getMessage().contains("No encontrado"));
    }

    @Test
    void testGetAll() {
        List<TestEntity> entities = Arrays.asList(
                new TestEntity(1L, "one"),
                new TestEntity(2L, "two")
        );
        List<TestDTO> dtos = Arrays.asList(
                new TestDTO(1L, "one"),
                new TestDTO(2L, "two")
        );

        when(repository.findAll()).thenReturn(entities);
        when(mapper.toDto(entities.get(0))).thenReturn(dtos.get(0));
        when(mapper.toDto(entities.get(1))).thenReturn(dtos.get(1));

        List<TestDTO> result = processor.getAll();

        assertEquals(2, result.size());
        assertEquals("one", result.get(0).getName());
    }

    @Test
    void testUpdate_found() {
        Long id = 1L;
        TestDTO dto = new TestDTO(id, "updated");
        TestEntity entity = new TestEntity(id, "updated");

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        TestDTO result = processor.update(id, dto);

        assertEquals(dto, result);
    }

    @Test
    void testUpdate_notFound() {
        Long id = 42L;
        TestDTO dto = new TestDTO(id, "fail");

        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> processor.update(id, dto));
        assertTrue(ex.getMessage().contains("No encontrado"));
    }

    @Test
    void testDelete() {
        Long id = 5L;
        processor.delete(id);
        verify(repository).deleteById(id);
    }

    // Dummy DTO and Entity classes for testing

    private static class TestDTO {
        private Long id;
        private String name;

        public TestDTO(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() { return id; }

        public String getName() { return name; }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof TestDTO other)) return false;
            return this.id.equals(other.id) && this.name.equals(other.name);
        }
    }

    private static class TestEntity {
        private Long id;
        private String name;

        public TestEntity(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() { return id; }

        public String getName() { return name; }
    }
}
