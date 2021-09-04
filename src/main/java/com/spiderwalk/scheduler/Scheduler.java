package com.spiderwalk.scheduler;

import com.spiderwalk.scheduler.domain.Event;
import com.spiderwalk.scheduler.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * @author Jev Prentice
 * @since 09 August 2021
 */
@Component
@EnableScheduling
@Slf4j
public class Scheduler {

    @Autowired
    private EventService service;

    @Scheduled(cron = "${cron.expression}")
    public void scheduleTaskUsingCronExpression() throws InterruptedException {
        log.info("Scheduled task running at %s".formatted(Instant.now()));
        // TODO move the next lines into a queue consumer.
        final Event event = service.startEvent(Event.EventType.Type1);
        log.info("Start event");
        Thread.sleep(5000);
        service.endEvent(event.getId(), true, null);
        log.info("End event");
    }
}