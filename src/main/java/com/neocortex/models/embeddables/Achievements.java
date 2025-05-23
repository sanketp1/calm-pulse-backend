package com.neocortex.models.embeddables;

import com.neocortex.models.enums.Status;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Achievements {

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String dateAchieved;

    private String tag;

}
