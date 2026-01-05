package com.projectmanager.domain.port.in;

import java.util.UUID;

public interface DeleteTaskUseCase {
    void execute(UUID taskId);
}
