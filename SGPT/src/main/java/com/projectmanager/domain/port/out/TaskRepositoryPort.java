package com.projectmanager.domain.port.out;

import com.projectmanager.domain.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Output port for task persistence operations.
 */
public interface TaskRepositoryPort {

    /**
     * Saves a task.
     * 
     * @param task the task to save
     * @return the saved task
     */
    Task save(Task task);

    /**
     * Finds a task by ID.
     * 
     * @param id the task ID
     * @return optional containing the task if found
     */
    Optional<Task> findById(UUID id);

    /**
     * Finds all non-deleted tasks by project ID.
     * 
     * @param projectId the project ID
     * @return list of tasks
     */
    List<Task> findByProjectIdAndDeletedFalse(UUID projectId);

    /**
     * Counts non-deleted tasks for a project.
     * 
     * @param projectId the project ID
     * @return the count of non-deleted tasks
     */
    long countByProjectIdAndDeletedFalse(UUID projectId);
}
