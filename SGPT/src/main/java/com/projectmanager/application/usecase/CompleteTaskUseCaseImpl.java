package com.projectmanager.application.usecase;

import com.projectmanager.domain.exception.ProjectNotFoundException;
import com.projectmanager.domain.exception.ProjectNotOwnedException;
import com.projectmanager.domain.exception.TaskAlreadyCompletedException;
import com.projectmanager.domain.exception.TaskNotFoundException;
import com.projectmanager.domain.model.Task;
import com.projectmanager.domain.port.in.CompleteTaskUseCase;
import com.projectmanager.domain.port.out.AuditLogPort;
import com.projectmanager.domain.port.out.CurrentUserPort;
import com.projectmanager.domain.port.out.NotificationPort;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;
import com.projectmanager.domain.port.out.TaskRepositoryPort;

import java.util.UUID;

/**
 * Implementation of CompleteTaskUseCase.
 * Marks a task as completed.
 * Only the project owner can complete tasks.
 * Cannot complete an already completed task.
 * Generates audit and notification on success.
 */
public class CompleteTaskUseCaseImpl implements CompleteTaskUseCase {

    private final TaskRepositoryPort taskRepository;
    private final ProjectRepositoryPort projectRepository;
    private final CurrentUserPort currentUserPort;
    private final AuditLogPort auditLogPort;
    private final NotificationPort notificationPort;

    public CompleteTaskUseCaseImpl(
            TaskRepositoryPort taskRepository,
            ProjectRepositoryPort projectRepository,
            CurrentUserPort currentUserPort,
            AuditLogPort auditLogPort,
            NotificationPort notificationPort) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.currentUserPort = currentUserPort;
        this.auditLogPort = auditLogPort;
        this.notificationPort = notificationPort;
    }

    @Override
    public Task execute(UUID taskId) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        // Check if already completed
        if (task.isCompleted()) {
            throw new TaskAlreadyCompletedException(taskId);
        }

        var project = projectRepository.findById(task.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException(task.getProjectId()));

        var currentUserId = currentUserPort.getCurrentUserId();

        // Only project owner can complete tasks
        if (!project.isOwnedBy(currentUserId)) {
            throw new ProjectNotOwnedException(project.getId(), currentUserId);
        }

        // Complete the task
        task.complete();
        var savedTask = taskRepository.save(task);

        // Audit and notification
        auditLogPort.register("TASK_COMPLETED", taskId);
        notificationPort.notify("Task '" + task.getTitle() + "' has been completed");

        return savedTask;
    }
}
