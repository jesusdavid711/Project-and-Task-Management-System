package com.projectmanager.application.usecase;

import com.projectmanager.domain.exception.ProjectNotOwnedException;
import com.projectmanager.domain.exception.TaskNotFoundException;
import com.projectmanager.domain.port.in.DeleteTaskUseCase;
import com.projectmanager.domain.port.out.CurrentUserPort;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;
import com.projectmanager.domain.port.out.TaskRepositoryPort;

import java.util.UUID;

public class DeleteTaskUseCaseImpl implements DeleteTaskUseCase {

    private final TaskRepositoryPort taskRepository;
    private final ProjectRepositoryPort projectRepository;
    private final CurrentUserPort currentUserPort;

    public DeleteTaskUseCaseImpl(TaskRepositoryPort taskRepository, ProjectRepositoryPort projectRepository,
            CurrentUserPort currentUserPort) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.currentUserPort = currentUserPort;
    }

    @Override
    public void execute(UUID taskId) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        // Check project ownership
        var project = projectRepository.findById(task.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found for task"));

        if (!project.isOwnedBy(currentUserPort.getCurrentUserId())) {
            throw new ProjectNotOwnedException(project.getId(), currentUserPort.getCurrentUserId());
        }

        task.delete();
        taskRepository.save(task);
    }
}
