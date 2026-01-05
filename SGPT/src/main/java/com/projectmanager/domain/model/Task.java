package com.projectmanager.domain.model;

import java.util.UUID;

/**
 * Domain entity representing a Task within a Project.
 * Pure domain model - no framework dependencies.
 */
public class Task {

    private final UUID id;
    private final UUID projectId;
    private final String title;
    private boolean completed;
    private boolean deleted;

    public Task(UUID id, UUID projectId, String title, boolean completed, boolean deleted) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.completed = completed;
        this.deleted = deleted;
    }

    public static Task create(UUID projectId, String title) {
        return new Task(UUID.randomUUID(), projectId, title, false, false);
    }

    /**
     * Marks the task as completed.
     * 
     * @throws IllegalStateException if task is already completed
     */
    public void complete() {
        if (this.completed) {
            throw new IllegalStateException("Task is already completed");
        }
        this.completed = true;
    }

    /**
     * Marks the task as deleted (soft delete).
     */
    public void markAsDeleted() {
        this.deleted = true;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public UUID getId() {
        return id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public String getTitle() {
        return title;
    }
}
