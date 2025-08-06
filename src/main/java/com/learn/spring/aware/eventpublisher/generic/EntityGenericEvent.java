package com.learn.spring.aware.eventpublisher.generic;

import org.springframework.context.ApplicationEvent;

public class EntityGenericEvent <T> extends ApplicationEvent {
    public EntityGenericEvent(Object source) {
        super(source);
    }
}
