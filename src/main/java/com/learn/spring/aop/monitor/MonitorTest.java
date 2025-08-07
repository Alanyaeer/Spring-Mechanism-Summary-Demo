package com.learn.spring.aop.monitor;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MonitorTest {
    @PostConstruct
    public void init(){
        String s = testAop("3432432");
        String s1 = annotationAop("r2r3243432");
    }
    public String testAop(String args){
        return "111" + args;
    }
    @TestAnnotation
    public String annotationAop(String args){
        return "222" + args;
    }
}
