package com.learn.spring.aop.monitor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AopTestAdviceTest implements CommandLineRunner {
    public void adviceTypeAround(String args){
        System.out.println("adviceTypeAround");
    }

    public void adviceTypeBefore(String args){
        System.out.println("adviceTypeBefore");
    }

    public void adviceTypeAfter(String args){
        System.out.println("adviceTypeAfter");
    }

    public void adviceTypeAfterThrowing(String args){
        System.out.println("adviceTypeAfterThrowing");
    }

    public void adviceTypeAfterFinally(String args){
        System.out.println("adviceTypeAfterFinally");
    }

    @Override
    public void run(String... args) throws Exception {
        adviceTypeAround("faefa");
    }
}
