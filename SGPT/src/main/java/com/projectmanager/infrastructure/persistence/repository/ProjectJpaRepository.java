package com.projectmanager.infrastructure.persistence.repository;

import com.projectmanager.infrastructure.persistence.entity.ProjectJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * JPA repository for Project entity.
 */
@Repository
public interface ProjectJpaRepository extends JpaRepository<ProjectJpaEntity, UUID> {

    List<ProjectJpaEntity> findByOwnerIdAndDeletedFalse(UUID ownerId);
}
