package com.neocortex.models.embeddables;

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
