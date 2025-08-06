package com.learn.spring.aware.eventpublisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyApplicationEventListener implements ApplicationListener<Event> {


    @Override
    public void onApplicationEvent(Event event) {
        log.info("xxx" + event.getMessage());
    }
}
