package com.projectmanager.domain.port.in;

import com.projectmanager.domain.model.Task;

import java.util.UUID;

/**
 * Input port for completing a task.
 */
public interface CompleteTaskUseCase {

    /**
     * Marks a task as completed.
     * Only the project owner can complete tasks.
     * Cannot complete an already completed task.
     * Generates audit log and notification.
     *
     * @param taskId the task ID to complete
     * @return the completed task
     */
    Task execute(UUID taskId);
}
