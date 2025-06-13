package com.neocortex.controllers;

import com.neocortex.payloads.reminder.CreateReminderRequest;
import com.neocortex.payloads.reminder.ReminderResponse;
import com.neocortex.payloads.reminder.UpdateReminderRequest;
import com.neocortex.services.IReminderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reminders")
@RequiredArgsConstructor
@Slf4j
public class ReminderController {

    private final IReminderService reminderService;

    @PostMapping("/{userId}")
    public ResponseEntity<ReminderResponse> createReminder(
            @PathVariable UUID userId,
            @Valid @RequestBody CreateReminderRequest request) {
        log.info("Creating reminder for userId={} with title='{}'", userId, request.getTitle());
        ReminderResponse response = reminderService.createReminder(userId, request);
        log.info("Reminder created with id={}", response.getId());

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{userId}/{reminderId}")
    public ResponseEntity<ReminderResponse> getReminderById(
            @PathVariable UUID userId,
            @PathVariable Long reminderId) {

        log.info("Fetching reminder with id={} for userId={}", reminderId, userId);
        ReminderResponse response = reminderService.getReminderById(userId, reminderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ReminderResponse>> getAllReminders(
            @PathVariable UUID userId) {

        log.info("Fetching all reminders for userId={}", userId);
        List<ReminderResponse> reminders = reminderService.getAllRemindersByUser(userId);
        return ResponseEntity.ok(reminders);
    }

    @PutMapping("/{userId}/{reminderId}")
    public ResponseEntity<ReminderResponse> updateReminder(
            @PathVariable UUID userId,
            @PathVariable Long reminderId,
            @Valid @RequestBody UpdateReminderRequest request) {

        log.info("Updating reminder with id={} for userId={}", reminderId, userId);
        ReminderResponse response = reminderService.updateReminder(userId, reminderId, request);
        log.info("Reminder with id={} updated", reminderId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/{reminderId}")
    public ResponseEntity<Void> deleteReminder(
            @PathVariable UUID userId,
            @PathVariable Long reminderId) {

        log.info("Deleting reminder with id={} for userId={}", reminderId, userId);
        reminderService.deleteReminder(userId, reminderId);
        log.info("Deleted reminder with id={}", reminderId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteMultipleReminders(
            @PathVariable UUID userId,
            @RequestBody List<Long> reminderIds) {

        log.info("Deleting multiple reminders for userId={}, ids={}", userId, reminderIds);
        reminderService.deleteMultipleReminders(userId, reminderIds);
        log.info("Deleted reminders with ids={}", reminderIds);
        return ResponseEntity.noContent().build();
    }
}
