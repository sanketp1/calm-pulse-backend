package com.neocortex.controllers;

import com.neocortex.payloads.journal.CreateJournalRequest;
import com.neocortex.payloads.journal.JournalResponse;
import com.neocortex.payloads.PaginatedResponse;
import com.neocortex.payloads.journal.UpdateJournalRequest;
import com.neocortex.services.IJournalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/journals")
@RequiredArgsConstructor
@Slf4j
public class JournalController {

    private final IJournalService journalService;

    @PostMapping("/{userId}")
    public ResponseEntity<JournalResponse> createJournal(
            @PathVariable UUID userId,
            @Valid @RequestBody CreateJournalRequest request) {
        log.info("POST /journals - Create journal for userId: {}", userId);
        return ResponseEntity.ok(journalService.createJournal(userId, request));
    }

    @GetMapping("/{userId}/{journalId}")
    public ResponseEntity<JournalResponse> getJournalById(
            @PathVariable UUID userId,
            @PathVariable Long journalId) {
        log.info("GET /journals - Get journal with id: {} for userId: {}", journalId, userId);
        return ResponseEntity.ok(journalService.getJournalById(userId, journalId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PaginatedResponse<JournalResponse>> getAllJournalsByUser(
            @PathVariable UUID userId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursor,
            @RequestParam(defaultValue = "10") int limit) {

        log.info("GET /journals - Fetch paginated journals for userId: {}", userId);
        return ResponseEntity.ok(journalService.getAllJournalsByUser(userId, cursor, limit));
    }

    @PutMapping("/{userId}/{journalId}")
    public ResponseEntity<JournalResponse> updateJournal(
            @PathVariable UUID userId,
            @PathVariable Long journalId,
            @Valid @RequestBody UpdateJournalRequest request) {
        log.info("PUT /journals - Update journal with id: {} for userId: {}", journalId, userId);
        return ResponseEntity.ok(journalService.updateJournal(userId, journalId, request));
    }

    @DeleteMapping("/{userId}/{journalId}")
    public ResponseEntity<Void> deleteJournal(
            @PathVariable UUID userId,
            @PathVariable Long journalId) {
        log.info("DELETE /journals - Delete journal with id: {} for userId: {}", journalId, userId);
        journalService.deleteJournal(userId, journalId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteMultipleJournals(
            @PathVariable UUID userId,
            @RequestBody List<Long> journalIds) {
        log.info("DELETE /journals - Delete multiple journals for userId: {}", userId);
        journalService.deleteMultipleJournals(userId, journalIds);
        return ResponseEntity.noContent().build();
    }
}
