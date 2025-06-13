package com.neocortex.repositories;

import com.neocortex.models.Journal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JournalRepository extends JpaRepository<Journal, Long> {


    @Query(
            value = """
                        SELECT j FROM Journal j
                        WHERE j.id = :journalId AND j.user.id = :userId
                    """
    )
    Optional<Journal> findByIdAndUserId(@Param("journalId") Long journalId, @Param("userId") UUID userId);

    @Query(
            value = """
                        SELECT COUNT(j) > 0 FROM Journal j
                        WHERE j.user.id = :userId AND j.id = :journalId
                    """
    )
    boolean existsByIdAndUserId(@Param("journalId") Long journalId, @Param("userId") UUID userId);

    @Query("SELECT j FROM Journal j WHERE j.user.id = :userId ORDER BY j.createdAt DESC")
    List<Journal> findByUserIdOrderByCreatedAtDesc(@Param("userId") UUID userId, Pageable pageable);

    @Query("SELECT j FROM Journal j WHERE j.user.id = :userId AND j.createdAt < :cursor ORDER BY j.createdAt DESC")
    List<Journal> findByUserIdAndCreatedAtBeforeOrderByCreatedAtDesc(@Param("userId") UUID userId, @Param("cursor") LocalDateTime cursor, Pageable pageable);

}
