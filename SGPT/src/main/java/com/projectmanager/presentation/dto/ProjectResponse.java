package com.projectmanager.presentation.dto;

import com.projectmanager.domain.model.Project;
import com.projectmanager.domain.model.ProjectStatus;

import java.util.UUID;

/**
 * Response DTO for project.
 */
public class ProjectResponse {

    private UUID id;
    private String name;
    private ProjectStatus status;

    public ProjectResponse() {
    }

    public ProjectResponse(UUID id, String name, ProjectStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public static ProjectResponse fromDomain(Project project) {
        return new ProjectResponse(project.getId(), project.getName(), project.getStatus());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }
}
