package com.learn.spring.cep;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class FactoryBeanGetTest {
    @Autowired
    private ApplicationContext applicationContext;
    @PostConstruct
    public void init() {
        log.info("--------------");
        Object bean = applicationContext.getBean("scheduledExecutor");
        Object factoryBean = applicationContext.getBean("&scheduledExecutor");
        log.info("bean: {}", bean);
        log.info("factoryBean: {}", factoryBean);
    }
}
