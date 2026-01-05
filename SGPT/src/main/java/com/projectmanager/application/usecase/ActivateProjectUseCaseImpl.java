package com.projectmanager.application.usecase;

import com.projectmanager.domain.exception.ProjectCannotBeActivatedException;
import com.projectmanager.domain.exception.ProjectNotFoundException;
import com.projectmanager.domain.exception.ProjectNotOwnedException;
import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.port.in.ActivateProjectUseCase;
import com.projectmanager.domain.port.out.AuditLogPort;
import com.projectmanager.domain.port.out.CurrentUserPort;
import com.projectmanager.domain.port.out.NotificationPort;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;
import com.projectmanager.domain.port.out.TaskRepositoryPort;

import java.util.UUID;

/**
 * Implementation of ActivateProjectUseCase.
 * Activates a project if it has at least one non-deleted task.
 * Only the owner can activate.
 * Generates audit and notification on success.
 */
public class ActivateProjectUseCaseImpl implements ActivateProjectUseCase {

    private final ProjectRepositoryPort projectRepository;
    private final TaskRepositoryPort taskRepository;
    private final CurrentUserPort currentUserPort;
    private final AuditLogPort auditLogPort;
    private final NotificationPort notificationPort;

    public ActivateProjectUseCaseImpl(
            ProjectRepositoryPort projectRepository,
            TaskRepositoryPort taskRepository,
            CurrentUserPort currentUserPort,
            AuditLogPort auditLogPort,
            NotificationPort notificationPort) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.currentUserPort = currentUserPort;
        this.auditLogPort = auditLogPort;
        this.notificationPort = notificationPort;
    }

    @Override
    public Project execute(UUID projectId) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        var currentUserId = currentUserPort.getCurrentUserId();

        // Only owner can activate
        if (!project.isOwnedBy(currentUserId)) {
            throw new ProjectNotOwnedException(projectId, currentUserId);
        }

        // Must have at least one non-deleted task
        long taskCount = taskRepository.countByProjectIdAndDeletedFalse(projectId);
        if (taskCount == 0) {
            throw new ProjectCannotBeActivatedException(projectId);
        }

        // Activate the project
        project.activate();
        var savedProject = projectRepository.save(project);

        // Audit and notification
        auditLogPort.register("PROJECT_ACTIVATED", projectId);
        notificationPort.notify("Project '" + project.getName() + "' has been activated");

        return savedProject;
    }
}
