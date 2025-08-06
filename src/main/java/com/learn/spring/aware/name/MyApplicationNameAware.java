package com.learn.spring.aware.name;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class MyApplicationNameAware implements BeanNameAware, ResourceLoaderAware {
    private ResourceLoader resourceLoader;
    @Override
    public void setBeanName(String s) {
      log.info(s);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        log.info(resourceLoader.getClass().getName());
    }

    @PostConstruct
    public void init() {

        Resource resource = resourceLoader.getResource("classpath:applicationContext.xml");
    }
}
