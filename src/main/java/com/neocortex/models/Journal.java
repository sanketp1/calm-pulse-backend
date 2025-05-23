package com.neocortex.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "journals")
public class Journal {

    @Id
    @GeneratedValue
    private Long id;

    private String prompt;

    @OneToOne
    @JoinColumn
    private Mood mood;

    private String title;

    private String description;

    @Embedded
    private Attachment attachment;

    private LocalDateTime timeStamp;

}
