package com.projectmanager.domain.port.in;

import com.projectmanager.domain.model.Project;

/**
 * Input port for creating a new project.
 */
public interface CreateProjectUseCase {

    /**
     * Creates a new project with DRAFT status.
     * 
     * @param name the project name
     * @return the created project
     */
    Project execute(String name);
}
