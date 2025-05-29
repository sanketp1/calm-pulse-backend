package com.neocortex.services.impl;

import com.neocortex.models.Journal;
import com.neocortex.models.Mood;
import com.neocortex.models.User;
import com.neocortex.payloads.*;
import com.neocortex.repositories.JournalRepository;
import com.neocortex.repositories.MoodRepository;
import com.neocortex.repositories.UserRepository;
import com.neocortex.services.IJournalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class JournalService implements IJournalService {

    private final JournalRepository journalRepository;
    private final MoodRepository moodRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public JournalResponse createJournal(UUID userId, CreateJournalRequest createJournalRequest) {
        log.info("Creating journal for userId: {}", userId);
        return saveOrUpdateJournal(userId, null, createJournalRequest, null);
    }

    @Override
    public JournalResponse getJournalById(UUID userId, Long journalId) {
        log.info("Fetching journal with id: {} for userId: {}", journalId, userId);
        return journalRepository.findByIdAndUserId(journalId, userId)
                .map(journal -> modelMapper.map(journal, JournalResponse.class))
                .orElseThrow(() -> {
                    log.error("Journal with id: {} not found for userId: {}", journalId, userId);
                    return new IllegalArgumentException("Journal not found");
                });
    }

    @Override
    public PaginatedResponse<JournalResponse> getAllJournalsByUser(UUID userId, LocalDateTime cursor, int limit) {
        log.info("Fetching paginated journals for userId: {}, cursor: {}, limit: {}", userId, cursor, limit);

        Pageable pageable = PageRequest.of(0, limit + 1);
        List<Journal> journals;

        if (cursor == null) {
            journals = journalRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        } else {
            journals = journalRepository.findByUserIdAndCreatedAtBeforeOrderByCreatedAtDesc(userId, cursor, pageable);
        }

        boolean hasNext = journals.size() > limit;
        if (hasNext) {
            journals = journals.subList(0, limit);
        }

        List<JournalResponse> journalResponses = journals.stream()
                .map(journal -> modelMapper.map(journal, JournalResponse.class))
                .toList();

        PaginatedResponse<JournalResponse> response = new PaginatedResponse<>();
        response.setData(journalResponses);
        response.setTotalElements(journalResponses.size());
        response.setHasNext(hasNext);
        response.setNextCursor(
                hasNext ? journals.get(journals.size() - 1).getCreatedAt().toString() : null
        );

        return response;
    }



    @Override
    public JournalResponse updateJournal(UUID userId, Long journalId, UpdateJournalRequest updateJournalRequest) {
        log.info("Updating journal with id: {} for userId: {}", journalId, userId);
        return saveOrUpdateJournal(userId, journalId, null, updateJournalRequest);
    }

    @Override
    public void deleteJournal(UUID userId, Long journalId) {
        log.info("Deleting journal with id: {} for userId: {}", journalId, userId);
        if (!journalRepository.existsByIdAndUserId(journalId, userId)) {
            log.error("Journal with id: {} not found for userId: {}", journalId, userId);
            throw new IllegalArgumentException("Journal not found");
        }
        journalRepository.deleteById(journalId);
        log.info("Journal with id: {} deleted successfully for userId: {}", journalId, userId);
    }

    @Override
    public void deleteMultipleJournals(UUID userId, List<Long> journalIds) {
        log.info("Deleting multiple journals with ids: {} for userId: {}", journalIds, userId);
        journalIds.forEach(journalId -> deleteJournal(userId, journalId));
        log.info("All specified journals deleted successfully for userId: {}", userId);
    }

    private JournalResponse saveOrUpdateJournal(UUID userId, Long journalId,
                                                CreateJournalRequest createRequest,
                                                UpdateJournalRequest updateRequest) {

        Journal journal = (journalId != null)
                ? journalRepository.findByIdAndUserId(journalId, userId)
                .orElseThrow(() -> {
                    log.error("Journal with id: {} not found for userId: {}", journalId, userId);
                    return new IllegalArgumentException("Journal not found");
                })
                : new Journal();

        final User user = User.builder().id(userId).build();

        if (createRequest != null) {
            modelMapper.map(createRequest, journal);
            journal.setUser(user);

            if (createRequest.getMood() != null) {
                Mood mood = modelMapper.map(createRequest.getMood(), Mood.class);
                mood.setUser(user);
                final Mood savedMood = moodRepository.save(mood);
                journal.setMood(savedMood);
            }
        }

        if (updateRequest != null) {
            modelMapper.map(updateRequest, journal);

            if (updateRequest.getMood() != null) {
                Mood mood = modelMapper.map(updateRequest.getMood(), Mood.class);
                mood.setUser(user);
                final Mood savedMood = moodRepository.save(mood);
                journal.setMood(savedMood);
            }
        }

        Journal saved = journalRepository.save(journal);
        log.info("Journal with id: {} saved successfully for userId: {}", saved.getId(), userId);
        return modelMapper.map(saved, JournalResponse.class);
    }
}
