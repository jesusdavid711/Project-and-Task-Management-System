package com.projectmanager.domain.exception;

import java.util.UUID;

/**
 * Thrown when a project is not found.
 */
public class ProjectNotFoundException extends DomainException {

    public ProjectNotFoundException(UUID projectId) {
        super(String.format("Project %s not found", projectId));
    }
}
