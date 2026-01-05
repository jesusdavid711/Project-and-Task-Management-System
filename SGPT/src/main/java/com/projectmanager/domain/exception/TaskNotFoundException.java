package com.projectmanager.domain.exception;

import java.util.UUID;

/**
 * Thrown when a task is not found.
 */
public class TaskNotFoundException extends DomainException {

    public TaskNotFoundException(UUID taskId) {
        super(String.format("Task %s not found", taskId));
    }
}
