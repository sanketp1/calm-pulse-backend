package com.neocortex.payloads;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private String avatarURL;
    private String phoneNumber;
    private String address;
    private LocalDateTime joinedDate;
    private LocalDateTime lastLogin;
    private String role;
}