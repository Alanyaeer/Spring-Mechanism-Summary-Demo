package com.learn.spring.aware.eventpublisher.generic;

import com.learn.spring.aware.eventpublisher.common.BizEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class GenericListener {
    @EventListener
    public void listen1(EntityCreatedEvent<BizEntity> bizEntity) {
        log.info("如果加上ResolvableTypeProvider ");
        log.info(bizEntity.toString());
    }

    @EventListener
    public void listen2(EntityGenericEvent<BizEntity> bizEntity) {
        log.info("如果不加上ResolvableTypeProvider ");
        log.info(bizEntity.toString());
    }
}
