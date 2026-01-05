package com.projectmanager.domain.port.out;

import com.projectmanager.domain.model.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Output port for project persistence operations.
 */
public interface ProjectRepositoryPort {

    /**
     * Saves a project.
     * 
     * @param project the project to save
     * @return the saved project
     */
    Project save(Project project);

    /**
     * Finds a project by ID.
     * 
     * @param id the project ID
     * @return optional containing the project if found
     */
    Optional<Project> findById(UUID id);

    /**
     * Finds all non-deleted projects by owner ID.
     * 
     * @param ownerId the owner's user ID
     * @return list of projects
     */
    List<Project> findByOwnerIdAndDeletedFalse(UUID ownerId);
}
