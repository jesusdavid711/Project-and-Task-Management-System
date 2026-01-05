package com.projectmanager.infrastructure.persistence.adapter;

import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;
import com.projectmanager.infrastructure.persistence.mapper.ProjectMapper;
import com.projectmanager.infrastructure.persistence.repository.ProjectJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adapter implementing ProjectRepositoryPort using JPA.
 */
@Component
public class ProjectRepositoryAdapter implements ProjectRepositoryPort {

    private final ProjectJpaRepository repository;
    private final ProjectMapper mapper;

    public ProjectRepositoryAdapter(ProjectJpaRepository repository, ProjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Project save(Project project) {
        var entity = mapper.toEntity(project);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Project> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Project> findByOwnerIdAndDeletedFalse(UUID ownerId) {
        return repository.findByOwnerIdAndDeletedFalse(ownerId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
