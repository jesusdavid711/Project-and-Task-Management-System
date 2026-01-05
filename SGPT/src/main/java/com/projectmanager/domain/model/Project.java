package com.projectmanager.domain.model;

import java.util.UUID;

/**
 * Domain entity representing a Project.
 * Pure domain model - no framework dependencies.
 * Contains business logic for project lifecycle.
 */
public class Project {

    private final UUID id;
    private final UUID ownerId;
    private final String name;
    private ProjectStatus status;
    private boolean deleted;

    public Project(UUID id, UUID ownerId, String name, ProjectStatus status, boolean deleted) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.status = status;
        this.deleted = deleted;
    }

    public static Project create(UUID ownerId, String name) {
        return new Project(UUID.randomUUID(), ownerId, name, ProjectStatus.DRAFT, false);
    }

    /**
     * Activates the project. Business rule validation should be done by the use
     * case.
     */
    public void activate() {
        this.status = ProjectStatus.ACTIVE;
    }

    /**
     * Marks the project as deleted (soft delete).
     */
    public void markAsDeleted() {
        this.deleted = true;
    }

    /**
     * Checks if the given user is the owner of this project.
     */
    public boolean isOwnedBy(UUID userId) {
        return this.ownerId.equals(userId);
    }

    public boolean isActive() {
        return this.status == ProjectStatus.ACTIVE;
    }

    public boolean isDraft() {
        return this.status == ProjectStatus.DRAFT;
    }

    public UUID getId() {
        return id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
