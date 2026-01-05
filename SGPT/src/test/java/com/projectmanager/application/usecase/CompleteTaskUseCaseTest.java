package com.projectmanager.application.usecase;

import com.projectmanager.domain.exception.TaskAlreadyCompletedException;
import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.model.ProjectStatus;
import com.projectmanager.domain.model.Task;
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
 * Unit tests for CompleteTaskUseCase.
 * Tests run without Spring context, mocking all output ports.
 */
@ExtendWith(MockitoExtension.class)
class CompleteTaskUseCaseTest {

    @Mock
    private TaskRepositoryPort taskRepository;

    @Mock
    private ProjectRepositoryPort projectRepository;

    @Mock
    private CurrentUserPort currentUserPort;

    @Mock
    private AuditLogPort auditLogPort;

    @Mock
    private NotificationPort notificationPort;

    private CompleteTaskUseCaseImpl completeTaskUseCase;

    private UUID ownerId;
    private UUID projectId;
    private UUID taskId;

    @BeforeEach
    void setUp() {
        completeTaskUseCase = new CompleteTaskUseCaseImpl(
                taskRepository,
                projectRepository,
                currentUserPort,
                auditLogPort,
                notificationPort);
        ownerId = UUID.randomUUID();
        projectId = UUID.randomUUID();
        taskId = UUID.randomUUID();
    }

    @Test
    @DisplayName("CompleteTask_AlreadyCompleted_ShouldFail")
    void completeTask_AlreadyCompleted_ShouldFail() {
        // Arrange
        var task = new Task(taskId, projectId, "Test Task", true, false); // already completed

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Act & Assert
        assertThrows(TaskAlreadyCompletedException.class, () -> {
            completeTaskUseCase.execute(taskId);
        });

        verify(auditLogPort, never()).register(anyString(), any(UUID.class));
        verify(notificationPort, never()).notify(anyString());
    }

    @Test
    @DisplayName("CompleteTask_ShouldGenerateAuditAndNotification")
    void completeTask_ShouldGenerateAuditAndNotification() {
        // Arrange
        var task = new Task(taskId, projectId, "Test Task", false, false);
        var project = new Project(projectId, ownerId, "Test Project", ProjectStatus.ACTIVE, false);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(currentUserPort.getCurrentUserId()).thenReturn(ownerId);
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        var result = completeTaskUseCase.execute(taskId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isCompleted());

        // Verify audit and notification were called
        verify(auditLogPort).register("TASK_COMPLETED", taskId);
        verify(notificationPort).notify(anyString());
    }
}
