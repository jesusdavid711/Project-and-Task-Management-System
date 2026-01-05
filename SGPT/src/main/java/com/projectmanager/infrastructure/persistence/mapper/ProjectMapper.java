package com.projectmanager.infrastructure.persistence.mapper;

import com.projectmanager.domain.model.Project;
import com.projectmanager.infrastructure.persistence.entity.ProjectJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper between Project domain model and JPA entity.
 */
@Component
public class ProjectMapper {

    public Project toDomain(ProjectJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Project(
                entity.getId(),
                entity.getOwnerId(),
                entity.getName(),
                entity.getStatus(),
                entity.isDeleted());
    }

    public ProjectJpaEntity toEntity(Project domain) {
        if (domain == null) {
            return null;
        }
        return new ProjectJpaEntity(
                domain.getId(),
                domain.getOwnerId(),
                domain.getName(),
                domain.getStatus(),
                domain.isDeleted());
    }
}
