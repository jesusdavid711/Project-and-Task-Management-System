package com.projectmanager.infrastructure.adapter;

import com.projectmanager.domain.port.out.NotificationPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Adapter implementing NotificationPort using console logging.
 */
@Component
public class NotificationAdapter implements NotificationPort {

    private static final Logger logger = LoggerFactory.getLogger(NotificationAdapter.class);

    @Override
    public void notify(String message) {
        logger.info("NOTIFICATION - {}", message);
    }
}
