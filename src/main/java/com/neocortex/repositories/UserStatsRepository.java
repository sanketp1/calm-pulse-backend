package com.neocortex.repositories;

import com.neocortex.models.UserStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserStatsRepository extends JpaRepository<UserStats, Long> {

    @Query(
        "SELECT us FROM UserStats us WHERE us.user.id = :userId"
    )
    Optional<UserStats> findByUserId(UUID userId);
}
