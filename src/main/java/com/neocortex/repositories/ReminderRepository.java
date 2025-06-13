package com.neocortex.repositories;

import com.neocortex.models.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    // Find reminder by id and user id
    @Query("SELECT r FROM Reminder r WHERE r.id = :reminderId AND r.user.id = :userId")
    Optional<Reminder> findByIdAndUserId(@Param("reminderId") Long reminderId, @Param("userId") UUID userId);

    // Find all reminders by user id
    @Query("SELECT r FROM Reminder r WHERE r.user.id = :userId ORDER BY r.id DESC")
    List<Reminder> findAllByUserId(@Param("userId") UUID userId);

    // Find all reminders by list of ids and user id
    @Query("SELECT r FROM Reminder r WHERE r.id IN :reminderIds AND r.user.id = :userId")
    List<Reminder> findAllByIdInAndUserId(@Param("reminderIds") List<Long> reminderIds, @Param("userId") UUID userId);

}
