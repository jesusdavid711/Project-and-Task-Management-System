package com.projectmanager.infrastructure.persistence.mapper;

import com.projectmanager.domain.model.User;
import com.projectmanager.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper between User domain model and JPA entity.
 */
@Component
public class UserMapper {

    public User toDomain(UserJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword());
    }

    public UserJpaEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        return new UserJpaEntity(
                domain.getId(),
                domain.getUsername(),
                domain.getEmail(),
                domain.getPassword());
    }
}
