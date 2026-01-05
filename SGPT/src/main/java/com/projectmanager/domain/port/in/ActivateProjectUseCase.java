package com.projectmanager.domain.port.in;

import com.projectmanager.domain.model.Project;

import java.util.UUID;

/**
 * Input port for activating a project.
 */
public interface ActivateProjectUseCase {

    /**
     * Activates a project if it has at least one non-deleted task.
     * Only the project owner can activate.
     * Generates audit log and notification.
     *
     * @param projectId the project ID to activate
     * @return the activated project
     */
    Project execute(UUID projectId);
}
