package com.neocortex.services.impl;

import com.neocortex.exceptions.UserNotFoundException;
import com.neocortex.models.Mood;
import com.neocortex.models.User;
import com.neocortex.payloads.CreateMoodRequest;
import com.neocortex.payloads.MoodResponse;
import com.neocortex.payloads.UpdateMoodRequest;
import com.neocortex.repositories.MoodRepository;
import com.neocortex.repositories.UserRepository;
import com.neocortex.services.IMoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MoodService implements IMoodService {

    private final MoodRepository moodRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public MoodResponse createMood(UUID userId, CreateMoodRequest request) {
        log.info("Creating mood for user: {}", userId);
        User user = validateUserExists(userId);

        Mood mood = Mood.builder()
                .moodType(request.getMoodType())
                .value(request.getValue())
                .location(request.getLocation())
                .note(request.getNote())
                .user(user)
                .build();

        Mood savedMood = moodRepository.save(mood);
        log.info("Mood created with ID: {} for user: {}", savedMood.getId(), userId);
        return modelMapper.map(savedMood, MoodResponse.class);
    }

    @Override
    public MoodResponse getMoodById(UUID userId, Long moodId) {
        log.info("Retrieving mood ID: {} for user: {}", moodId, userId);
        validateUserExists(userId);

        Mood mood = moodRepository.findById(moodId)
                .orElseThrow(() -> {
                    log.warn("Mood not found: {}", moodId);
                    return new IllegalArgumentException("Mood not found with ID: " + moodId);
                });

        validateMoodOwnership(mood, userId);
        return modelMapper.map(mood, MoodResponse.class);
    }

    @Override
    public List<MoodResponse> getAllMoodsByUser(UUID userId) {
        log.info("Fetching all moods for user: {}", userId);
        validateUserExists(userId);

        return moodRepository.findAllByUserId(userId).stream()
                .map(mood -> modelMapper.map(mood, MoodResponse.class))
                .toList();
    }

    @Override
    public MoodResponse updateMood(UUID userId, Long moodId, UpdateMoodRequest request) {
        log.info("Updating mood ID: {} for user: {}", moodId, userId);
        validateUserExists(userId);

        Mood mood = moodRepository.findById(moodId)
                .orElseThrow(() -> {
                    log.warn("Mood not found: {}", moodId);
                    return new IllegalArgumentException("Mood not found with ID: " + moodId);
                });

        validateMoodOwnership(mood, userId);

        mood.setMoodType(request.getMoodType());
        mood.setValue(request.getValue());
        mood.setLocation(request.getLocation());
        mood.setNote(request.getNote());

        Mood updatedMood = moodRepository.save(mood);
        log.info("Mood updated. ID: {}", moodId);
        return modelMapper.map(updatedMood, MoodResponse.class);
    }

    @Override
    public void deleteMood(UUID userId, Long moodId) {
        log.info("Deleting mood ID: {} for user: {}", moodId, userId);
        validateUserExists(userId);

        Mood mood = moodRepository.findById(moodId)
                .orElseThrow(() -> {
                    log.warn("Mood not found: {}", moodId);
                    return new IllegalArgumentException("Mood not found with ID: " + moodId);
                });

        validateMoodOwnership(mood, userId);

        moodRepository.delete(mood);
        log.info("Mood deleted. ID: {}", moodId);
    }

    @Override
    public void deleteMultipleMoods(UUID userId, List<Long> moodIds) {
        log.info("Deleting multiple moods for user: {}", userId);
        validateUserExists(userId);

        List<Mood> moods = moodRepository.findAllById(moodIds);

        moods.forEach(mood -> validateMoodOwnership(mood, userId));

        moodRepository.deleteAll(moods);
        log.info("Deleted {} moods for user: {}", moods.size(), userId);
    }

    private User validateUserExists(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User not found: {}", userId);
                    return new UserNotFoundException("User not found with ID: " + userId);
                });
    }

    private void validateMoodOwnership(Mood mood, UUID userId) {
        if (!mood.getUser().getId().equals(userId)) {
            log.warn("Unauthorized access to mood. Mood ID: {}, User ID: {}", mood.getId(), userId);
            throw new IllegalArgumentException("Mood does not belong to the user.");
        }
    }

}
