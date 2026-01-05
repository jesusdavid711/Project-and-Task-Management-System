package com.projectmanager.application.usecase;

import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.model.ProjectStatus;
import com.projectmanager.domain.port.out.CurrentUserPort;
import com.projectmanager.domain.port.out.ProjectRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateProjectUseCaseTest {

    @Mock
    private ProjectRepositoryPort projectRepository;

    @Mock
    private CurrentUserPort currentUserPort;

    @InjectMocks
    private CreateProjectUseCaseImpl createProjectUseCase;

    @Test
    void execute_ShouldCreateProjectWithDraftStatus() {
        // Arrange
        UUID userId = UUID.randomUUID();
        String projectName = "New Project";
        when(currentUserPort.getCurrentUserId()).thenReturn(userId);
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Project createdProject = createProjectUseCase.execute(projectName);

        // Assert
        assertEquals(ProjectStatus.DRAFT, createdProject.getStatus(), "New project must be DRAFT");
        assertEquals(projectName, createdProject.getName());
        assertEquals(userId, createdProject.getOwnerId());
        verify(projectRepository).save(any(Project.class));
    }
}
