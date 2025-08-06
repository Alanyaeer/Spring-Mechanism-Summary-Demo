package com.learn.spring.aware.eventpublisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BeanApplicationEventListener {
    @EventListener(condition = "#event.message == 'hello'")
    public void onApplicationEvent(Event event) {
        log.info("event: {}", event);
    }
}
