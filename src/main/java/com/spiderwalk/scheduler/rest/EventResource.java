package com.spiderwalk.scheduler.rest;

import com.spiderwalk.scheduler.domain.Event;
import com.spiderwalk.scheduler.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * @author Jev Prentice
 * @since 09 August 2021
 */
@RestController
@RequestMapping("/api/event")
@Slf4j
public class EventResource {

    @Autowired
    EventService service;

    @GetMapping("/")
    public ResponseEntity<?> findEvents() {
        final List<Event> events = service.findEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{type}")
    public ResponseEntity<?> findByType(@PathVariable final Event.EventType type) {
        return new ResponseEntity<>(service.findByIdOrThrow(type), HttpStatus.OK);
    }

    @PostMapping("/start/{type}")
    public ResponseEntity<?> start(@PathVariable final Event.EventType type) {
        service.findByIdOrThrow(type).ifPresent(t -> {
            throw new RuntimeException("Event %s is already running since %s."
                    .formatted(type, t.getStartTime()));
        });
        final Event event = service.startEvent(type);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping("/end/{type}")
    public ResponseEntity<?> end(@PathVariable final Event.EventType type) {
        final Event previous = service.findByIdOrThrow(type).orElseThrow(NoResultException::new);
        final Event event = service.endEvent(previous.getId(), true, null);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }
}
