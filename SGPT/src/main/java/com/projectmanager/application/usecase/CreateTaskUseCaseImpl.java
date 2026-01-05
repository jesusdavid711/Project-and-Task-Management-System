package com.projectmanager.application.usecase;

import com.projectmanager.domain.exception.ProjectNotFoundException;
import com.projectmanager.domain.exception.ProjectNotOwnedException;
import com.projectmanager.domain.model.Task;
import com.projectmanager.domain.port.in.CreateTaskUseCase;
import com.projectmanager.domain.port.out.CurrentUserPort;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;
import com.projectmanager.domain.port.out.TaskRepositoryPort;

import java.util.UUID;

/**
 * Implementation of CreateTaskUseCase.
 * Creates a new task in the specified project.
 * Only the project owner can create tasks.
 */
public class CreateTaskUseCaseImpl implements CreateTaskUseCase {

    private final TaskRepositoryPort taskRepository;
    private final ProjectRepositoryPort projectRepository;
    private final CurrentUserPort currentUserPort;

    public CreateTaskUseCaseImpl(
            TaskRepositoryPort taskRepository,
            ProjectRepositoryPort projectRepository,
            CurrentUserPort currentUserPort) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.currentUserPort = currentUserPort;
    }

    @Override
    public Task execute(UUID projectId, String title) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        var currentUserId = currentUserPort.getCurrentUserId();

        // Only owner can add tasks
        if (!project.isOwnedBy(currentUserId)) {
            throw new ProjectNotOwnedException(projectId, currentUserId);
        }

        var task = Task.create(projectId, title);
        return taskRepository.save(task);
    }
}
