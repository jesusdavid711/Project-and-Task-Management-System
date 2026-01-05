package com.projectmanager.infrastructure.persistence.adapter;

import com.projectmanager.domain.model.Task;
import com.projectmanager.domain.port.out.TaskRepositoryPort;
import com.projectmanager.infrastructure.persistence.mapper.TaskMapper;
import com.projectmanager.infrastructure.persistence.repository.TaskJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adapter implementing TaskRepositoryPort using JPA.
 */
@Component
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final TaskJpaRepository repository;
    private final TaskMapper mapper;

    public TaskRepositoryAdapter(TaskJpaRepository repository, TaskMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Task save(Task task) {
        var entity = mapper.toEntity(task);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Task> findByProjectIdAndDeletedFalse(UUID projectId) {
        return repository.findByProjectIdAndDeletedFalse(projectId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public long countByProjectIdAndDeletedFalse(UUID projectId) {
        return repository.countByProjectIdAndDeletedFalse(projectId);
    }
}
