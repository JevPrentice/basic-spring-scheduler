package com.spiderwalk.scheduler.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author Jev Prentice
 * @since 09 August 2021
 */
// lombok
@AllArgsConstructor
@Getter
@Setter
@ToString
// jpa
@Entity
public class Event {

    public enum EventType {
        Type1,
        Type2
    }

    public Event() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType type;

    @Column(nullable = false)
    private boolean isActive;
    private String exceptionMessage;

    @NonNull
    @Column(nullable = false)
    private Instant startTime;

    @NonNull
    private Instant endTime;
    private boolean success;
}
