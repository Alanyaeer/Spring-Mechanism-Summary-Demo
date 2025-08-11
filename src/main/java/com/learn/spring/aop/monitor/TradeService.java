package com.learn.spring.aop.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Slf4j
@Component
public class TradeService implements CommandLineRunner {
    public String test1(){
        return "tradeService";
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(test1());

    }
}
