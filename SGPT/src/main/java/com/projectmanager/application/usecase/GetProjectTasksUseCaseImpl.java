package com.projectmanager.application.usecase;

import com.projectmanager.domain.exception.ProjectNotOwnedException;
import com.projectmanager.domain.exception.ProjectNotFoundException;
import com.projectmanager.domain.model.Task;
import com.projectmanager.domain.port.in.GetProjectTasksUseCase;
import com.projectmanager.domain.port.out.CurrentUserPort;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;
import com.projectmanager.domain.port.out.TaskRepositoryPort;

import java.util.List;
import java.util.UUID;

public class GetProjectTasksUseCaseImpl implements GetProjectTasksUseCase {

    private final TaskRepositoryPort taskRepository;
    private final ProjectRepositoryPort projectRepository;
    private final CurrentUserPort currentUserPort;

    public GetProjectTasksUseCaseImpl(
            TaskRepositoryPort taskRepository,
            ProjectRepositoryPort projectRepository,
            CurrentUserPort currentUserPort) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.currentUserPort = currentUserPort;
    }

    @Override
    public List<Task> execute(UUID projectId) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        if (!project.isOwnedBy(currentUserPort.getCurrentUserId())) {
            throw new ProjectNotOwnedException(projectId, currentUserPort.getCurrentUserId());
        }

        return taskRepository.findByProjectIdAndDeletedFalse(projectId);
    }
}
