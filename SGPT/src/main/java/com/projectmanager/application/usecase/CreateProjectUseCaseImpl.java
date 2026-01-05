package com.projectmanager.application.usecase;

import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.port.in.CreateProjectUseCase;
import com.projectmanager.domain.port.out.CurrentUserPort;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;

/**
 * Implementation of CreateProjectUseCase.
 * Creates a new project with DRAFT status for the current user.
 */
public class CreateProjectUseCaseImpl implements CreateProjectUseCase {

    private final ProjectRepositoryPort projectRepository;
    private final CurrentUserPort currentUserPort;

    public CreateProjectUseCaseImpl(ProjectRepositoryPort projectRepository, CurrentUserPort currentUserPort) {
        this.projectRepository = projectRepository;
        this.currentUserPort = currentUserPort;
    }

    @Override
    public Project execute(String name) {
        var userId = currentUserPort.getCurrentUserId();
        var project = Project.create(userId, name);
        return projectRepository.save(project);
    }
}
