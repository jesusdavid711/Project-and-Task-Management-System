package com.projectmanager.domain.exception;

import java.util.UUID;

/**
 * Thrown when a user tries to access a project they don't own.
 */
public class ProjectNotOwnedException extends DomainException {

    public ProjectNotOwnedException(UUID projectId, UUID userId) {
        super(String.format("User %s is not the owner of project %s", userId, projectId));
    }
}
