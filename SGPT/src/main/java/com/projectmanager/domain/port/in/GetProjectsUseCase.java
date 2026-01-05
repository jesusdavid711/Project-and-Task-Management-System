package com.projectmanager.domain.port.in;

import com.projectmanager.domain.model.Project;

import java.util.List;

/**
 * Input port for retrieving projects.
 */
public interface GetProjectsUseCase {

    /**
     * Returns all non-deleted projects for the current user.
     *
     * @return list of user's projects
     */
    List<Project> execute();
}
