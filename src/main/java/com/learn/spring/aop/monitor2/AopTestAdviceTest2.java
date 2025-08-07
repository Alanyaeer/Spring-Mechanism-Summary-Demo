package com.learn.spring.aop.monitor2;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AopTestAdviceTest2 {
    @PostConstruct
    public void init(){
        test("AopTestAdviceTest2");
    }

    public String test(String arg){
        return arg + "#test";
    }
}
