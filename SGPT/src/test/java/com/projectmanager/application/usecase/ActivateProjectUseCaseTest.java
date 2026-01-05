package com.projectmanager.application.usecase;

import com.projectmanager.domain.exception.ProjectCannotBeActivatedException;
import com.projectmanager.domain.exception.ProjectNotOwnedException;
import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.model.ProjectStatus;
import com.projectmanager.domain.port.out.AuditLogPort;
import com.projectmanager.domain.port.out.CurrentUserPort;
import com.projectmanager.domain.port.out.NotificationPort;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;
import com.projectmanager.domain.port.out.TaskRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ActivateProjectUseCase.
 * Tests run without Spring context, mocking all output ports.
 */
@ExtendWith(MockitoExtension.class)
class ActivateProjectUseCaseTest {

    @Mock
    private ProjectRepositoryPort projectRepository;

    @Mock
    private TaskRepositoryPort taskRepository;

    @Mock
    private CurrentUserPort currentUserPort;

    @Mock
    private AuditLogPort auditLogPort;

    @Mock
    private NotificationPort notificationPort;

    private ActivateProjectUseCaseImpl activateProjectUseCase;

    private UUID ownerId;
    private UUID projectId;

    @BeforeEach
    void setUp() {
        activateProjectUseCase = new ActivateProjectUseCaseImpl(
                projectRepository,
                taskRepository,
                currentUserPort,
                auditLogPort,
                notificationPort);
        ownerId = UUID.randomUUID();
        projectId = UUID.randomUUID();
    }

    @Test
    @DisplayName("ActivateProject_WithTasks_ShouldSucceed")
    void activateProject_WithTasks_ShouldSucceed() {
        // Arrange
        var project = new Project(projectId, ownerId, "Test Project", ProjectStatus.DRAFT, false);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(currentUserPort.getCurrentUserId()).thenReturn(ownerId);
        when(taskRepository.countByProjectIdAndDeletedFalse(projectId)).thenReturn(1L);
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        var result = activateProjectUseCase.execute(projectId);

        // Assert
        assertNotNull(result);
        assertEquals(ProjectStatus.ACTIVE, result.getStatus());
        verify(auditLogPort).register("PROJECT_ACTIVATED", projectId);
        verify(notificationPort).notify(anyString());
    }

    @Test
    @DisplayName("ActivateProject_WithoutTasks_ShouldFail")
    void activateProject_WithoutTasks_ShouldFail() {
        // Arrange
        var project = new Project(projectId, ownerId, "Test Project", ProjectStatus.DRAFT, false);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(currentUserPort.getCurrentUserId()).thenReturn(ownerId);
        when(taskRepository.countByProjectIdAndDeletedFalse(projectId)).thenReturn(0L);

        // Act & Assert
        assertThrows(ProjectCannotBeActivatedException.class, () -> {
            activateProjectUseCase.execute(projectId);
        });

        verify(auditLogPort, never()).register(anyString(), any(UUID.class));
        verify(notificationPort, never()).notify(anyString());
    }

    @Test
    @DisplayName("ActivateProject_ByNonOwner_ShouldFail")
    void activateProject_ByNonOwner_ShouldFail() {
        // Arrange
        var differentUserId = UUID.randomUUID();
        var project = new Project(projectId, ownerId, "Test Project", ProjectStatus.DRAFT, false);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(currentUserPort.getCurrentUserId()).thenReturn(differentUserId);

        // Act & Assert
        assertThrows(ProjectNotOwnedException.class, () -> {
            activateProjectUseCase.execute(projectId);
        });

        verify(taskRepository, never()).countByProjectIdAndDeletedFalse(any(UUID.class));
        verify(auditLogPort, never()).register(anyString(), any(UUID.class));
        verify(notificationPort, never()).notify(anyString());
    }
}
