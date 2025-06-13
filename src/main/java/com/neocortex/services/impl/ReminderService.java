package com.neocortex.services.impl;

import com.neocortex.exceptions.ResourceNotFoundException;
import com.neocortex.models.Reminder;
import com.neocortex.models.User;
import com.neocortex.payloads.reminder.CreateReminderRequest;
import com.neocortex.payloads.reminder.ReminderResponse;
import com.neocortex.payloads.reminder.UpdateReminderRequest;
import com.neocortex.repositories.ReminderRepository;
import com.neocortex.repositories.UserRepository;
import com.neocortex.services.IReminderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReminderService implements IReminderService {

    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ReminderResponse createReminder(UUID userId, CreateReminderRequest createReminderRequest) {
        log.info("Creating reminder for user {}", userId);

        // Check if user exists to maintain integrity
        if (!userRepository.existsById(userId)) {
            log.error("User not found with id: {}", userId);
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        // Build user reference without fetching full entity
        User user = User.builder().id(userId).build();

        // Map request to entity
        Reminder reminder = modelMapper.map(createReminderRequest, Reminder.class);
        reminder.setUser(user);

        Reminder savedReminder = reminderRepository.save(reminder);
        log.info("Reminder created with id {}", savedReminder.getId());

        return modelMapper.map(savedReminder, ReminderResponse.class);
    }

    @Override
    public ReminderResponse getReminderById(UUID userId, Long reminderId) {
        log.info("Fetching reminder id {} for user {}", reminderId, userId);

        Reminder reminder = reminderRepository.findByIdAndUserId(reminderId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder not found with id " + reminderId + " for user " + userId));

        return modelMapper.map(reminder, ReminderResponse.class);
    }

    @Override
    public List<ReminderResponse> getAllRemindersByUser(UUID userId) {
        log.info("Fetching all reminders for user {}", userId);

        List<Reminder> reminders = reminderRepository.findAllByUserId(userId);

        return reminders.stream()
                .map(reminder -> modelMapper.map(reminder, ReminderResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReminderResponse updateReminder(UUID userId, Long reminderId, UpdateReminderRequest updateReminderRequest) {
        log.info("Updating reminder id {} for user {}", reminderId, userId);

        Reminder existingReminder = reminderRepository.findByIdAndUserId(reminderId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder not found with id " + reminderId + " for user " + userId));

        // Update fields if present (you can add null checks if you want partial updates)
        if (updateReminderRequest.getTitle() != null) {
            existingReminder.setTitle(updateReminderRequest.getTitle());
        }
        if (updateReminderRequest.getTime() != null) {
            existingReminder.setTime(updateReminderRequest.getTime());
        }
        if (updateReminderRequest.getDays() != null) {
            existingReminder.setDays(updateReminderRequest.getDays());
        }
        existingReminder.setEnabled(updateReminderRequest.isEnabled());

        Reminder updatedReminder = reminderRepository.save(existingReminder);
        log.info("Reminder updated id {}", updatedReminder.getId());

        return modelMapper.map(updatedReminder, ReminderResponse.class);
    }

    @Override
    public void deleteReminder(UUID userId, Long reminderId) {
        log.info("Deleting reminder id {} for user {}", reminderId, userId);

        Reminder reminder = reminderRepository.findByIdAndUserId(reminderId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder not found with id " + reminderId + " for user " + userId));

        reminderRepository.delete(reminder);

        log.info("Deleted reminder id {}", reminderId);
    }

    @Override
    public void deleteMultipleReminders(UUID userId, List<Long> reminderIds) {
        log.info("Deleting multiple reminders {} for user {}", reminderIds, userId);

        List<Reminder> reminders = reminderRepository.findAllByIdInAndUserId(reminderIds, userId);

        if (reminders.size() != reminderIds.size()) {
            throw new ResourceNotFoundException("One or more reminders not found for user " + userId);
        }

        reminderRepository.deleteAll(reminders);

        log.info("Deleted reminders {}", reminderIds);
    }
}
