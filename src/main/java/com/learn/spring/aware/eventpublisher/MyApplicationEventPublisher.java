package com.learn.spring.aware.eventpublisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyApplicationEventPublisher implements ApplicationEventPublisherAware, CommandLineRunner {

    private ApplicationEventPublisher publisher;


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void pushEvent(Event event) {
        log.info("我要发布事件了");
        publisher.publishEvent(event);
    }

    @Override
    public void run(String... args) throws Exception {
        Event event = new Event(this, "hello");
        pushEvent(event);
    }
}
