package com.projectmanager.infrastructure.adapter;

import com.projectmanager.domain.port.out.AuditLogPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Adapter implementing AuditLogPort using console logging.
 */
@Component
public class AuditLogAdapter implements AuditLogPort {

    private static final Logger logger = LoggerFactory.getLogger(AuditLogAdapter.class);

    @Override
    public void register(String action, UUID entityId) {
        logger.info("AUDIT LOG - Action: {}, EntityId: {}, Timestamp: {}",
                action, entityId, LocalDateTime.now());
    }
}
