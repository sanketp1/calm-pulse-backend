package com.neocortex.controllers;

import com.neocortex.payloads.CreateMoodRequest;
import com.neocortex.payloads.MoodResponse;
import com.neocortex.payloads.PaginatedResponse;
import com.neocortex.payloads.UpdateMoodRequest;
import com.neocortex.services.IMoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/moods")
@RequiredArgsConstructor
@Slf4j
public class MoodController {

    private final IMoodService moodService;

    @PostMapping
    public ResponseEntity<MoodResponse> createMood(
            @RequestParam UUID userId,
            @Valid @RequestBody CreateMoodRequest request) {
        log.info("Creating mood for user: {}", userId);
        return ResponseEntity.ok(moodService.createMood(userId, request));
    }


    @GetMapping("/{moodId}")
    public ResponseEntity<MoodResponse> getMoodById(
            @RequestParam UUID userId,
            @PathVariable Long moodId) {
        log.info("Fetching mood {} for user {}", moodId, userId);
        return ResponseEntity.ok(moodService.getMoodById(userId, moodId));
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<MoodResponse>> getAllMoods(
            @RequestParam UUID userId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int limit
    ) {
        log.info("Fetching paginated moods for user {}, cursor={}, limit={}", userId, cursor, limit);
        PaginatedResponse<MoodResponse> response = moodService.getAllMoodsByUser(userId, cursor, limit);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{moodId}")
    public ResponseEntity<MoodResponse> updateMood(
            @RequestParam UUID userId,
            @PathVariable Long moodId,
            @Valid @RequestBody UpdateMoodRequest request) {
        log.info("Updating mood {} for user {}", moodId, userId);
        return ResponseEntity.ok(moodService.updateMood(userId, moodId, request));
    }

    @DeleteMapping("/{moodId}")
    public ResponseEntity<Void> deleteMood(
            @RequestParam UUID userId,
            @PathVariable Long moodId) {
        log.info("Deleting mood {} for user {}", moodId, userId);
        moodService.deleteMood(userId, moodId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMultipleMoods(
            @RequestParam UUID userId,
            @RequestBody List<Long> moodIds) {
        log.info("Deleting multiple moods {} for user {}", moodIds, userId);
        moodService.deleteMultipleMoods(userId, moodIds);
        return ResponseEntity.noContent().build();
    }
}

