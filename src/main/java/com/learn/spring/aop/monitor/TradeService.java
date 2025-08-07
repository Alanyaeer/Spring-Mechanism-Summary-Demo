package com.learn.spring.aop.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Slf4j
@Component
public class TradeService {
    @PostConstruct
    public void init(){
        log.info(test1());
    }

    public String test1(){
        return "tradeService";
    }
}
