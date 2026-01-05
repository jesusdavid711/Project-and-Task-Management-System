package com.projectmanager.presentation.controller;

import com.projectmanager.domain.port.in.CompleteTaskUseCase;
import com.projectmanager.domain.port.in.CreateTaskUseCase;
import com.projectmanager.domain.port.in.GetProjectTasksUseCase;
import com.projectmanager.presentation.dto.CreateTaskRequest;
import com.projectmanager.presentation.dto.TaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for task endpoints.
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Tasks", description = "Task management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;
    private final GetProjectTasksUseCase getProjectTasksUseCase;

    public TaskController(
            CreateTaskUseCase createTaskUseCase,
            CompleteTaskUseCase completeTaskUseCase,
            GetProjectTasksUseCase getProjectTasksUseCase) {
        this.createTaskUseCase = createTaskUseCase;
        this.completeTaskUseCase = completeTaskUseCase;
        this.getProjectTasksUseCase = getProjectTasksUseCase;
    }

    @PostMapping("/projects/{projectId}/tasks")
    @Operation(summary = "Create a new task in a project")
    public ResponseEntity<TaskResponse> createTask(
            @PathVariable UUID projectId,
            @Valid @RequestBody CreateTaskRequest request) {
        var task = createTaskUseCase.execute(projectId, request.getTitle());
        return ResponseEntity.ok(TaskResponse.fromDomain(task));
    }

    @GetMapping("/projects/{projectId}/tasks")
    @Operation(summary = "Get all tasks of a project")
    public ResponseEntity<List<TaskResponse>> getTasks(@PathVariable UUID projectId) {
        var tasks = getProjectTasksUseCase.execute(projectId);
        var response = tasks.stream()
                .map(TaskResponse::fromDomain)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/tasks/{id}/complete")
    @Operation(summary = "Mark a task as completed")
    public ResponseEntity<TaskResponse> completeTask(@PathVariable UUID id) {
        var task = completeTaskUseCase.execute(id);
        return ResponseEntity.ok(TaskResponse.fromDomain(task));
    }
}
