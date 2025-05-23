package com.neocortex.models;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Location {
        private String name;
        double latitude;
        double longitude;

}
