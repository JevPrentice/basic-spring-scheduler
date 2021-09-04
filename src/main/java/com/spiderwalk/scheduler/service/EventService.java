package com.spiderwalk.scheduler.service;

import com.spiderwalk.scheduler.domain.Event;
import com.spiderwalk.scheduler.repository.EventRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * @author Jev Prentice
 * @since 09 August 2021
 */
@Service
@Transactional
@Slf4j
public class EventService {

    @Autowired
    EventRepo repo;

    public List<Event> findEvents() {
        return repo.findAll();
    }

    public Optional<Event> findByIdOrThrow(final Event.EventType type) {
        return repo.findByType(type);
    }

    public Event startEvent(final Event.EventType type) {
        final Event event = new Event();
        event.setType(type);
        event.setActive(true);
        event.setStartTime(Instant.now());
        return repo.save(event);
    }

    public Event endEvent(final long id, final boolean success, final String exceptionMessage) {
        final Event event = repo.findById(id).orElseThrow(NoResultException::new);
        event.setActive(false);
        event.setEndTime(Instant.now());
        event.setSuccess(success);
        if (!success && exceptionMessage != null)
            event.setExceptionMessage(exceptionMessage);
        return repo.save(event);
    }
}
