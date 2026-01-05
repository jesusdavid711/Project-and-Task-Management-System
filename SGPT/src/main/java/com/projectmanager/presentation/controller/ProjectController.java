package com.projectmanager.presentation.controller;

import com.projectmanager.domain.port.in.ActivateProjectUseCase;
import com.projectmanager.domain.port.in.CreateProjectUseCase;
import com.projectmanager.domain.port.in.GetProjectsUseCase;
import com.projectmanager.presentation.dto.CreateProjectRequest;
import com.projectmanager.presentation.dto.ProjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for project endpoints.
 */
@RestController
@RequestMapping("/api/projects")
@Tag(name = "Projects", description = "Project management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final GetProjectsUseCase getProjectsUseCase;
    private final ActivateProjectUseCase activateProjectUseCase;

    public ProjectController(
            CreateProjectUseCase createProjectUseCase,
            GetProjectsUseCase getProjectsUseCase,
            ActivateProjectUseCase activateProjectUseCase) {
        this.createProjectUseCase = createProjectUseCase;
        this.getProjectsUseCase = getProjectsUseCase;
        this.activateProjectUseCase = activateProjectUseCase;
    }

    @PostMapping
    @Operation(summary = "Create a new project")
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody CreateProjectRequest request) {
        var project = createProjectUseCase.execute(request.getName());
        return ResponseEntity.ok(ProjectResponse.fromDomain(project));
    }

    @GetMapping
    @Operation(summary = "Get all projects for current user")
    public ResponseEntity<List<ProjectResponse>> getProjects() {
        var projects = getProjectsUseCase.execute();
        var response = projects.stream()
                .map(ProjectResponse::fromDomain)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate a project")
    public ResponseEntity<ProjectResponse> activateProject(@PathVariable UUID id) {
        var project = activateProjectUseCase.execute(id);
        return ResponseEntity.ok(ProjectResponse.fromDomain(project));
    }
}
