package com.learn.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringawareinterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringawareinterfaceApplication.class, args);
    }

}
