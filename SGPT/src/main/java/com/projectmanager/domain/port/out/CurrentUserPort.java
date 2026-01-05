package com.projectmanager.domain.port.out;

import java.util.UUID;

/**
 * Output port for retrieving the current authenticated user.
 */
public interface CurrentUserPort {

    /**
     * Returns the ID of the currently authenticated user.
     * 
     * @return the current user's UUID
     */
    UUID getCurrentUserId();
}
