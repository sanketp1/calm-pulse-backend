package com.neocortex.controllers;

import com.neocortex.payloads.user_stats.UserStatsResponse;
import com.neocortex.services.IUserStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/user_stats")
@RequiredArgsConstructor
@Slf4j
public class UserStatsController {

    private final IUserStatsService userStatsService;

    /**
     * Retrieves the user statistics for the specified user.
     * <p>
     * This endpoint fetches statistical data and achievements for a user
     * identified by their unique ID. The data includes metrics such as
     * journal entries, mood check-ins, resource usage, mood averages,
     * streaks, level, and achievements.
     * </p>
     *
     * @param user_id The unique identifier of the user whose stats are to be retrieved.
     * @return A `ResponseEntity` containing the user's statistics in the form of a `UserStatsResponse`.
     */
    @GetMapping("/{user_id}")
    public ResponseEntity<UserStatsResponse> getUserStats(@PathVariable UUID user_id) {
        log.info("Get user stats with id {}", user_id);
        UserStatsResponse userStats = userStatsService.getUserStatsByUserId(user_id);
        log.info("User stats retrieved successfully for user: {}", user_id);
        return ResponseEntity.ok(userStats);
    }


  /**
 * Resets the user statistics for the specified user.
 * <p>
 * This endpoint allows resetting all statistical data and achievements
 * for a user identified by their unique ID. It sets all numerical fields
 * to their default values and clears the list of achievements.
 * </p>
 *
 * @param user_id The unique identifier of the user whose stats are to be reset.
 * @return A `ResponseEntity` with no content, indicating the operation was successful.
 */
@PutMapping("/reset")
public ResponseEntity<Void> resetUserStats(@RequestParam UUID user_id) {
    log.info("Resetting user stats for user: {}", user_id);
    userStatsService.resetUserStats(user_id);
    log.info("User stats reset successfully for user: {}", user_id);
    return ResponseEntity.noContent().build();
}
}
