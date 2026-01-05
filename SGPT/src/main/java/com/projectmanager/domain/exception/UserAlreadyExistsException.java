package com.projectmanager.domain.exception;

/**
 * Thrown when trying to register a user with an existing username or email.
 */
public class UserAlreadyExistsException extends DomainException {

    public UserAlreadyExistsException(String field, String value) {
        super(String.format("User with %s '%s' already exists", field, value));
    }
}
