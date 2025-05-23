package com.neocortex.models;

import com.neocortex.models.embeddables.Attachment;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.util.List;

/**
 * Represents a resource entity in the application, such as an article, exercise, or tool.
 * <p>
 * The Resources entity stores metadata and content for resources that users can access,
 * including title, description, tags, duration, instructions, benefits, and attachments.
 * It is used to organize and present helpful materials within the system.
 * </p>
 *
 * <ul>
 *   <li><b>id</b>: Unique identifier for the resource (Long).</li>
 *   <li><b>title</b>: Title of the resource (String).</li>
 *   <li><b>description</b>: Description of the resource (String).</li>
 *   <li><b>tags</b>: List of tags categorizing the resource (List&lt;String&gt;).</li>
 *   <li><b>duration</b>: Estimated duration to complete or consume the resource (Duration).</li>
 *   <li><b>instructions</b>: Step-by-step instructions for using the resource (List&lt;String&gt;).</li>
 *   <li><b>benefits</b>: List of benefits or outcomes from using the resource (List&lt;String&gt;).</li>
 *   <li><b>attachments</b>: List of file attachments or media related to the resource (List&lt;Attachment&gt;).</li>
 * </ul>
 *
 * @author sanketp1
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resources")
public class Resources {

    /**
     * Unique identifier for the resource.
     * Type: Long
     * Primary key, auto-generated.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Title of the resource.
     * Type: String
     */
    private String title;

    /**
     * Description of the resource.
     * Type: String
     */
    private String description;

    /**
     * List of tags categorizing the resource.
     * Type: List&lt;String&gt;
     * Stored as an element collection in a separate table.
     */
    @ElementCollection
    @CollectionTable(name = "resource_tags", joinColumns = @JoinColumn(name = "resource_id"))
    @Column(name = "tag")
    private List<String> tags;

    /**
     * Estimated duration to complete or consume the resource.
     * Type: Duration
     */
    private Duration duration;

    /**
     * Step-by-step instructions for using the resource.
     * Type: List&lt;String&gt;
     * Stored as an element collection in a separate table.
     */
    @ElementCollection
    @CollectionTable(name = "resource_instructions", joinColumns = @JoinColumn(name = "resource_id"))
    @Column(name = "instruction")
    private List<String> instructions;

    /**
     * List of benefits or outcomes from using the resource.
     * Type: List&lt;String&gt;
     * Stored as an element collection in a separate table.
     */
    @ElementCollection
    @CollectionTable(name = "resource_benefits", joinColumns = @JoinColumn(name = "resource_id"))
    @Column(name = "benefit")
    private List<String> benefits;

    /**
     * List of file attachments or media related to the resource.
     * Type: List&lt;Attachment&gt;
     * Stored as an element collection.
     */
    @ElementCollection
    private List<Attachment> attachments;
}