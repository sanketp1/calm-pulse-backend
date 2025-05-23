package com.neocortex.models;

import jakarta.persistence.*;

import java.time.Duration;
import java.util.List;

@Entity
@Table(name = "resources")
public class Resources {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;

    @ElementCollection
    @CollectionTable(name = "resource_tags", joinColumns = @JoinColumn(name = "resource_id"))
    @Column(name = "tag")
    private List<String> tags;

    private Duration duration;

    @ElementCollection
    @CollectionTable(name = "resource_instructions", joinColumns = @JoinColumn(name = "resource_id"))
    @Column(name = "instruction")
    private List<String> instructions;

    @ElementCollection
    @CollectionTable(name = "resource_benefits", joinColumns = @JoinColumn(name = "resource_id"))
    @Column(name = "benefit")
    private List<String> benefits;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "resource_id")
    private List<AttachmentEntity> attachments;
}
