package com.neocortex.payloads;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    private String name;
    private String avatarURL;
    private String phoneNumber;
    private String address;
}