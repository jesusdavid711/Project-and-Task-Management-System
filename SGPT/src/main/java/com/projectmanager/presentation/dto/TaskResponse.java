package com.projectmanager.presentation.dto;

import com.projectmanager.domain.model.Task;

import java.util.UUID;

/**
 * Response DTO for task.
 */
public class TaskResponse {

    private UUID id;
    private UUID projectId;
    private String title;
    private boolean completed;

    public TaskResponse() {
    }

    public TaskResponse(UUID id, UUID projectId, String title, boolean completed) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.completed = completed;
    }

    public static TaskResponse fromDomain(Task task) {
        return new TaskResponse(task.getId(), task.getProjectId(), task.getTitle(), task.isCompleted());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
