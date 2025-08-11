package com.learn.spring.aware.eventpublisher.generic;

import com.learn.spring.aware.eventpublisher.common.BizEntity;
import com.learn.spring.aware.eventpublisher.common.BizEntity2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
        BizEntity2 bizEntity2 = new BizEntity2();
        bizEntity.setMessage("Hello World");
        EntityCreatedEvent<BizEntity> bizEntityEntityCreatedEvent = new EntityCreatedEvent<>(bizEntity);
        EntityCreatedEvent<BizEntity2> bizEntityEntityCreatedEvent2 = new EntityCreatedEvent<>(bizEntity2);
        applicationEventPublisher.publishEvent(new EntityCreatedEvent<BizEntity>(bizEntity));
        Object source = new EntityCreatedEvent<BizEntity>(bizEntity).getSource();
        Map<ResolvableType, String> hashMap = new HashMap<>();

        hashMap.put(bizEntityEntityCreatedEvent.getResolvableType(), "bizEntity");
        hashMap.put(bizEntityEntityCreatedEvent.getResolvableType(), "bizEntity");
        hashMap.put(bizEntityEntityCreatedEvent2.getResolvableType(), "bizEntity2");
        Class<?> clazz = bizEntityEntityCreatedEvent.getResolvableType().resolveGeneric(0);
//        Object o = clazz.getDeclaredConstructors()[0].newInstance();
        Object o = clazz.getDeclaredConstructor().newInstance();
        log.info("obj: {}", o);
        log.info("type 类型为"  +bizEntityEntityCreatedEvent.getResolvableType().resolveGeneric(0).getName());
        log.info("source:{}", source);
        applicationEventPublisher.publishEvent(new EntityGenericEvent<BizEntity>(bizEntity));
        applicationEventPublisher.publishEvent(new EntityCreatedEvent<>(new BizEntity2()));
        applicationEventPublisher.publishEvent(new EntityGenericEvent<BizEntity2>(new  BizEntity2()));


    }
}
