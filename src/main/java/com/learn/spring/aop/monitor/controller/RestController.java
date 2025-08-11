package com.learn.spring.aop.monitor.controller;

import com.learn.spring.aop.monitor.HelloWorld;
import com.learn.spring.aop.monitor.MonitorTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.RequestScope;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/test")
public class RestController {
    @Autowired
    private MonitorTest monitorTest;
/**
 * Handles GET requests for the "/t1" endpoint
 * This method calls a test method with AOP annotation
 *
 * @return The result of the monitorTest.annotationAop method call
 */
    @GetMapping("/t1")
    public String t1(){
    // Call the annotationAop method from monitorTest object with parameter "hellowrld"
        return monitorTest.annotationAop("hellowrld");
    }

    @GetMapping("/t2")
    public String t2(){
        return monitorTest.annotationAop2(new HelloWorld("hellowrld"));
    }


}
