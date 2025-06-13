package com.neocortex.repositories;

import com.neocortex.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
        SELECT u FROM User u \s
        WHERE (:createdAfter IS NULL OR u.joinedDate > :createdAfter)
        ORDER BY u.joinedDate ASC
    """)
    List<User> findNextUsers(LocalDateTime createdAfter, Pageable pageable);
}
