package com.projectmanager.infrastructure.config;

import com.projectmanager.application.usecase.*;
import com.projectmanager.domain.port.in.*;
import com.projectmanager.domain.port.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for use case beans.
 */
@Configuration
public class UseCaseConfig {

    @Bean
    public CreateProjectUseCase createProjectUseCase(
            ProjectRepositoryPort projectRepository,
            CurrentUserPort currentUserPort) {
        return new CreateProjectUseCaseImpl(projectRepository, currentUserPort);
    }

    @Bean
    public GetProjectsUseCase getProjectsUseCase(
            ProjectRepositoryPort projectRepository,
            CurrentUserPort currentUserPort) {
        return new GetProjectsUseCaseImpl(projectRepository, currentUserPort);
    }

    @Bean
    public ActivateProjectUseCase activateProjectUseCase(
            ProjectRepositoryPort projectRepository,
            TaskRepositoryPort taskRepository,
            CurrentUserPort currentUserPort,
            AuditLogPort auditLogPort,
            NotificationPort notificationPort) {
        return new ActivateProjectUseCaseImpl(
                projectRepository, taskRepository, currentUserPort, auditLogPort, notificationPort);
    }

    @Bean
    public CreateTaskUseCase createTaskUseCase(
            TaskRepositoryPort taskRepository,
            ProjectRepositoryPort projectRepository,
            CurrentUserPort currentUserPort) {
        return new CreateTaskUseCaseImpl(taskRepository, projectRepository, currentUserPort);
    }

    @Bean
    public GetProjectTasksUseCase getProjectTasksUseCase(
            TaskRepositoryPort taskRepository,
            ProjectRepositoryPort projectRepository,
            CurrentUserPort currentUserPort) {
        return new GetProjectTasksUseCaseImpl(taskRepository, projectRepository, currentUserPort);
    }

    @Bean
    public CompleteTaskUseCase completeTaskUseCase(
            TaskRepositoryPort taskRepository,
            ProjectRepositoryPort projectRepository,
            CurrentUserPort currentUserPort,
            AuditLogPort auditLogPort,
            NotificationPort notificationPort) {
        return new CompleteTaskUseCaseImpl(
                taskRepository, projectRepository, currentUserPort, auditLogPort, notificationPort);
    }

    @Bean
    public DeleteProjectUseCase deleteProjectUseCase(ProjectRepositoryPort projectRepository,
            CurrentUserPort currentUserPort) {
        return new DeleteProjectUseCaseImpl(projectRepository, currentUserPort);
    }

    @Bean
    public DeleteTaskUseCase deleteTaskUseCase(TaskRepositoryPort taskRepository,
            ProjectRepositoryPort projectRepository, CurrentUserPort currentUserPort) {
        return new DeleteTaskUseCaseImpl(taskRepository, projectRepository, currentUserPort);
    }
}
