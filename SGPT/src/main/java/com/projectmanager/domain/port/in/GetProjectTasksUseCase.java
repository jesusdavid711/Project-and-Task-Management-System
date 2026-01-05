package com.projectmanager.domain.port.in;

import com.projectmanager.domain.model.Task;

import java.util.List;
import java.util.UUID;

public interface GetProjectTasksUseCase {
    List<Task> execute(UUID projectId);
}
