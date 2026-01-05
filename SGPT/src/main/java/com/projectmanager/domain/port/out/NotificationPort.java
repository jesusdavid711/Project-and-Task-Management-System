package com.projectmanager.domain.port.out;

/**
 * Output port for notification operations.
 */
public interface NotificationPort {

    /**
     * Sends a notification message.
     * 
     * @param message the notification message
     */
    void notify(String message);
}
