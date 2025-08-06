package com.learn.spring.aware.eventpublisher;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class Event extends ApplicationEvent {
    private String message;
    public Event(Object source, String message) {
        super(source);
        this.message = message;
    }
    public Event(Object source){
        super(source);
    }
}
