package com.learn.spring.aware.eventpublisher.generic;

import com.learn.spring.aware.eventpublisher.common.BizEntity;
import com.learn.spring.aware.eventpublisher.common.BizEntity2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GenericApplicationEventPublisher implements CommandLineRunner {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void run(String... args) throws Exception {
//        applicationEventPublisher.publishEvent(new Event(this, "hello2"));
        log.info("开始测试泛型EntityEvent");
        BizEntity bizEntity = new BizEntity();
        bizEntity.setMessage("Hello World");
        applicationEventPublisher.publishEvent(new EntityCreatedEvent<>(bizEntity));
        applicationEventPublisher.publishEvent(new EntityGenericEvent<BizEntity>(bizEntity));
        applicationEventPublisher.publishEvent(new EntityCreatedEvent<>(new BizEntity2()));
        applicationEventPublisher.publishEvent(new EntityGenericEvent<BizEntity2>(new  BizEntity2()));


    }
}
