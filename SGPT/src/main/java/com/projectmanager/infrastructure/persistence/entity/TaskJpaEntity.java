package com.projectmanager.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

/**
 * JPA entity for Task.
 */
@Entity
@Table(name = "tasks")
public class TaskJpaEntity {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "project_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID projectId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public TaskJpaEntity() {
    }

    public TaskJpaEntity(UUID id, UUID projectId, String title, boolean completed, boolean deleted) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.completed = completed;
        this.deleted = deleted;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
