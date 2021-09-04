package com.spiderwalk.scheduler.repository;

import com.spiderwalk.scheduler.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Jev Prentice
 * @since 09 August 2021
 */
public interface EventRepo extends JpaRepository<Event, Long> {

    Optional<Event> findByType(Event.EventType type);
}
