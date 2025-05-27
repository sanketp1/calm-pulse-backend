package com.neocortex.repositories;

import com.neocortex.models.Mood;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface MoodRepository extends JpaRepository<Mood, Long> {

    @Query("SELECT m FROM Mood m WHERE m.user.id = :userId")
    List<Mood> findAllByUserId(@Param("userId") UUID userId);

    List<Mood> findByUserIdAndIdGreaterThanOrderByIdAsc(UUID userId, Long cursor, Pageable pageable);

    List<Mood> findByUserIdOrderByIdAsc(UUID userId, Pageable pageable);

}
