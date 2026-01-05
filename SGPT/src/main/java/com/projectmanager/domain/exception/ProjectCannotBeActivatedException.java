package com.projectmanager.domain.exception;

import java.util.UUID;

/**
 * Thrown when a project cannot be activated (e.g., no tasks exist).
 */
public class ProjectCannotBeActivatedException extends DomainException {

    public ProjectCannotBeActivatedException(UUID projectId) {
        super(String.format("Project %s cannot be activated. It must have at least one non-deleted task.", projectId));
    }
}
