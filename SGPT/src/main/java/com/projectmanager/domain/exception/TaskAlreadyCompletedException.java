package com.projectmanager.domain.exception;

import java.util.UUID;

/**
 * Thrown when trying to complete a task that is already completed.
 */
public class TaskAlreadyCompletedException extends DomainException {

    public TaskAlreadyCompletedException(UUID taskId) {
        super(String.format("Task %s is already completed", taskId));
    }
}
