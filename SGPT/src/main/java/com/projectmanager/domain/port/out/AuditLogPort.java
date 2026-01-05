package com.projectmanager.domain.port.out;

import java.util.UUID;

/**
 * Output port for audit log operations.
 */
public interface AuditLogPort {

    /**
     * Registers an audit log entry.
     * 
     * @param action   the action performed
     * @param entityId the ID of the entity affected
     */
    void register(String action, UUID entityId);
}
