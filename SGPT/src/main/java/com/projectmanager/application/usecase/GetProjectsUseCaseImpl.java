package com.projectmanager.application.usecase;

import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.port.in.GetProjectsUseCase;
import com.projectmanager.domain.port.out.CurrentUserPort;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;

import java.util.List;

/**
 * Implementation of GetProjectsUseCase.
 * Returns all non-deleted projects for the current user.
 */
public class GetProjectsUseCaseImpl implements GetProjectsUseCase {

    private final ProjectRepositoryPort projectRepository;
    private final CurrentUserPort currentUserPort;

    public GetProjectsUseCaseImpl(ProjectRepositoryPort projectRepository, CurrentUserPort currentUserPort) {
        this.projectRepository = projectRepository;
        this.currentUserPort = currentUserPort;
    }

    @Override
    public List<Project> execute() {
        var userId = currentUserPort.getCurrentUserId();
        return projectRepository.findByOwnerIdAndDeletedFalse(userId);
    }
}
