package com.learn.spring.cep;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class BeanFactoryDemo  {
    @Bean
    public ScheduledExecutorFactoryBean scheduledExecutor() {
        return new ScheduledExecutorFactoryBean();
    }
}
