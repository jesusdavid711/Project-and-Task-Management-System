package com.projectmanager.domain.port.out;

import com.projectmanager.domain.model.User;

import java.util.Optional;
import java.util.UUID;

/**
 * Output port for user persistence operations.
 */
public interface UserRepositoryPort {

    /**
     * Saves a user.
     * 
     * @param user the user to save
     * @return the saved user
     */
    User save(User user);

    /**
     * Finds a user by ID.
     * 
     * @param id the user ID
     * @return optional containing the user if found
     */
    Optional<User> findById(UUID id);

    /**
     * Finds a user by username.
     * 
     * @param username the username
     * @return optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by email.
     * 
     * @param email the email
     * @return optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if a username already exists.
     * 
     * @param username the username to check
     * @return true if exists
     */
    boolean existsByUsername(String username);

    /**
     * Checks if an email already exists.
     * 
     * @param email the email to check
     * @return true if exists
     */
    boolean existsByEmail(String email);
}
