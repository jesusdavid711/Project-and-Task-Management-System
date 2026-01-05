package com.projectmanager.infrastructure.persistence.repository;

import com.projectmanager.infrastructure.persistence.entity.TaskJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * JPA repository for Task entity.
 */
@Repository
public interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, UUID> {

    List<TaskJpaEntity> findByProjectIdAndDeletedFalse(UUID projectId);

    long countByProjectIdAndDeletedFalse(UUID projectId);
}
