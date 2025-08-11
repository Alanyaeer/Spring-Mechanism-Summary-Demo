package com.learn.spring.aop.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MonitorTest implements CommandLineRunner {
    public String testAop(String args){
        return "111" + args;
    }
    @TestAnnotation(value = "3")
    public String annotationAop(String args){
        return "222" + args;
    }

    @TestAnnotation(value = "4")
    public String annotationAop2(HelloWorld helloWorld){
        return helloWorld.getText();
    }

    @Override
    public void run(String... args) throws Exception {
//            monitorTest.annotationAop("testAnnotationAop");
    }
}
