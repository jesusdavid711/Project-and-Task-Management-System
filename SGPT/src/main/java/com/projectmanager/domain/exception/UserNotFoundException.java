package com.projectmanager.domain.exception;

/**
 * Thrown when a user is not found.
 */
public class UserNotFoundException extends DomainException {

    public UserNotFoundException(String identifier) {
        super(String.format("User not found: %s", identifier));
    }
}
