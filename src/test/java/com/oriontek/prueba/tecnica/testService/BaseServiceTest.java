package com.oriontek.prueba.tecnica.testService;

import com.oriontek.prueba.tecnica.application.mapper.BaseMapper;
import com.oriontek.prueba.tecnica.application.service.impl.BaseService;
import com.oriontek.prueba.tecnica.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaseServiceTest {

    // --- Entidad y DTO de prueba ---
    static class TestEntity {
        Long id;
        String name;
        // getters y setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    static class TestDTO {
        Long id;
        String name;
        // getters y setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    // --- Clase concreta de BaseService para test ---
    static class TestEntityService extends BaseService<TestEntity, TestDTO> {

        protected TestEntityService(JpaRepository<TestEntity, Long> repository, BaseMapper<TestEntity, TestDTO> mapper) {
            super(repository, mapper);
        }

        @Override
        public TestDTO update(Long id, TestDTO dto) {
            TestEntity entity = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Entity not found with ID: " + id));
            // Simulamos update manual simple
            entity.setName(dto.getName());
            return mapper.toDto(repository.save(entity));
        }
    }

    @Mock
    JpaRepository<TestEntity, Long> repository;

    @Mock
    BaseMapper<TestEntity, TestDTO> mapper;

    @InjectMocks
    TestEntityService service;

    TestEntity entity;
    TestDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        entity = new TestEntity();
        entity.setId(1L);
        entity.setName("Entidad");

        dto = new TestDTO();
        dto.setId(1L);
        dto.setName("DTO");
    }

    @Test
    void create_ShouldMapAndSaveEntity() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        TestDTO result = service.create(dto);

        verify(mapper).toEntity(dto);
        verify(repository).save(entity);
        verify(mapper).toDto(entity);
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getById_ExistingId_ShouldReturnDto() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        TestDTO result = service.getById(1L);

        verify(repository).findById(1L);
        verify(mapper).toDto(entity);
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getById_NotFound_ShouldThrow() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Entity not found with ID: 1");

        verify(repository).findById(1L);
        verifyNoInteractions(mapper);
    }

    @Test
    void getAll_ShouldReturnListOfDtos() {
        List<TestEntity> entities = Arrays.asList(entity);
        List<TestDTO> dtos = Arrays.asList(dto);

        when(repository.findAll()).thenReturn(entities);
        when(mapper.toDto(entity)).thenReturn(dto);

        List<TestDTO> result = service.getAll();

        verify(repository).findAll();
        verify(mapper).toDto(entity);
        assertThat(result).hasSize(1).containsExactly(dto);
    }

    @Test
    void update_ExistingEntity_ShouldUpdateAndReturnDto() {
        TestDTO updatedDto = new TestDTO();
        updatedDto.setName("Nuevo nombre");

        TestEntity updatedEntity = new TestEntity();
        updatedEntity.setId(1L);
        updatedEntity.setName("Nuevo nombre");

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(updatedEntity);
        when(mapper.toDto(updatedEntity)).thenReturn(updatedDto);

        TestDTO result = service.update(1L, updatedDto);

        verify(repository).findById(1L);
        verify(repository).save(entity);
        verify(mapper).toDto(updatedEntity);
        assertThat(result.getName()).isEqualTo("Nuevo nombre");
    }

    @Test
    void update_NotFound_ShouldThrow() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(1L, dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Entity not found with ID: 1");

        verify(repository).findById(1L);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper);
    }

    @Test
    void delete_ExistingEntity_ShouldCallDelete() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        doNothing().when(repository).delete(entity);

        service.delete(1L);

        verify(repository).findById(1L);
        verify(repository).delete(entity);
    }

    @Test
    void delete_NotFound_ShouldThrow() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Entity not found with ID: 1");

        verify(repository).findById(1L);
        verifyNoMoreInteractions(repository);
    }
}