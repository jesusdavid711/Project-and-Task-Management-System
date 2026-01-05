package com.projectmanager.infrastructure.persistence.mapper;

import com.projectmanager.domain.model.Task;
import com.projectmanager.infrastructure.persistence.entity.TaskJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper between Task domain model and JPA entity.
 */
@Component
public class TaskMapper {

    public Task toDomain(TaskJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Task(
                entity.getId(),
                entity.getProjectId(),
                entity.getTitle(),
                entity.isCompleted(),
                entity.isDeleted());
    }

    public TaskJpaEntity toEntity(Task domain) {
        if (domain == null) {
            return null;
        }
        return new TaskJpaEntity(
                domain.getId(),
                domain.getProjectId(),
                domain.getTitle(),
                domain.isCompleted(),
                domain.isDeleted());
    }
}
