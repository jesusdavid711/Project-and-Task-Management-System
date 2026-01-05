package com.projectmanager.application.usecase;

import com.projectmanager.domain.exception.ProjectNotFoundException;
import com.projectmanager.domain.exception.ProjectNotOwnedException;
import com.projectmanager.domain.port.in.DeleteProjectUseCase;
import com.projectmanager.domain.port.out.CurrentUserPort;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;

import java.util.UUID;

public class DeleteProjectUseCaseImpl implements DeleteProjectUseCase {

    private final ProjectRepositoryPort projectRepository;
    private final CurrentUserPort currentUserPort;

    public DeleteProjectUseCaseImpl(ProjectRepositoryPort projectRepository, CurrentUserPort currentUserPort) {
        this.projectRepository = projectRepository;
        this.currentUserPort = currentUserPort;
    }

    @Override
    public void execute(UUID projectId) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        if (!project.isOwnedBy(currentUserPort.getCurrentUserId())) {
            throw new ProjectNotOwnedException(projectId, currentUserPort.getCurrentUserId());
        }

        project.delete();
        projectRepository.save(project);
    }
}
