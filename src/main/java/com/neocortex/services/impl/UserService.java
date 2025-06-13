package com.neocortex.services.impl;

import com.neocortex.exceptions.UserMismatchException;
import com.neocortex.exceptions.UserNotFoundException;
import com.neocortex.models.User;
import com.neocortex.payloads.PaginatedResponse;
import com.neocortex.payloads.user.UpdateUserRequest;
import com.neocortex.payloads.user.UserResponse;
import com.neocortex.repositories.UserRepository;
import com.neocortex.services.IUserService;
import com.neocortex.utils.VerifyUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final VerifyUser verifyUser;
    private final ModelMapper modelMapper;

    @Override
    public UserResponse getCurrentUser(Authentication authentication) {
        final String username = authentication.getName();
        log.info("Fetching current user with email: {}", username);
        final User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + username));
        // Check if the user is trying to access their own data
        verifyUser.isSelf(user.getId());
        log.info("Current user fetched: {}", user.getEmail());
        return modelMapper.map(user, UserResponse.class);
    }


    @Override
    public UserResponse getUserById(UUID id) {
        // Check if the user is trying to access their own data
        verifyUser.isSelf(id);

        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserResponse.class))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }


    @Override
    public PaginatedResponse<UserResponse> getAllUsers(String cursorId, Integer size) {

        LocalDateTime createdAfter = null;

        if (cursorId != null && !cursorId.isEmpty()) {
            UUID uuid = UUID.fromString(cursorId);
            User lastUser = userRepository.findById(uuid)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid cursor ID"));
            createdAfter = lastUser.getJoinedDate();
        }

        Pageable pageable = PageRequest.of(0, size + 1, Sort.by("joinedDate").ascending());
        List<User> users = userRepository.findNextUsers(createdAfter, pageable);

        boolean hasNext = users.size() > size;
        if (hasNext) {
            users = users.subList(0, size);
        }

        List<UserResponse> responses = users.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();

        String nextCursor = hasNext
                ? users.get(users.size() - 1).getId().toString()
                : null;

        PaginatedResponse<UserResponse> response = new PaginatedResponse<>();
        response.setData(responses);
        response.setNextCursor(nextCursor);
        response.setHasNext(hasNext);
        response.setTotalElements(users.size());

        return response;
    }

    @Override
    public UserResponse updateUser(UUID id, UpdateUserRequest updateUserRequest) {
        // Check if the user is trying to access their own data
        verifyUser.isSelf(id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        modelMapper.map(updateUserRequest, user);
        user.setLastUpdated(LocalDateTime.now());
        userRepository.save(user);

        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public void changePassword(UUID id, String newPassword) {
        // Check if the user is trying to access their own data
        verifyUser.isSelf(id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setPassword(newPassword); // Assuming password is stored in plain text (not recommended)
        user.setLastUpdated(LocalDateTime.now());
        userRepository.save(user);

        log.info("Password changed for user with ID: {}", id);
    }

    //admin only method
    @Override
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.delete(user);
        log.info("User with ID: {} has been deleted", id);
    }

    //admin only method
    @Override
    public void deleteMultipleUsers(List<UUID> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("The list of IDs cannot be null or empty");
        }

        // Process in batches
        final int batchSize = 50; // Define batch size
        for (int i = 0; i < ids.size(); i += batchSize) {
            List<UUID> batch = ids.subList(i, Math.min(i + batchSize, ids.size()));
            userRepository.deleteAllByIdInBatch(batch);
            log.info("Deleted batch of users with IDs: {}", batch);
        }
    }
}
