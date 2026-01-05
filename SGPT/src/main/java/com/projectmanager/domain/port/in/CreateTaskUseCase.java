package com.projectmanager.domain.port.in;

import com.projectmanager.domain.model.Task;

import java.util.UUID;

/**
 * Input port for creating a new task.
 */
public interface CreateTaskUseCase {

    /**
     * Creates a new task in the specified project.
     * Only the project owner can create tasks.
     *
     * @param projectId the project ID
     * @param title     the task title
     * @return the created task
     */
    Task execute(UUID projectId, String title);
}
