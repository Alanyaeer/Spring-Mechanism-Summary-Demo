package com.learn.spring.custom.spelresolver.core;

import com.learn.spring.custom.spelresolver.CustomProvider;
import com.learn.spring.custom.spelresolver.annotation.CustomFieldKey;
import com.learn.spring.custom.spelresolver.annotation.CustomKey;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CustomKeyAspectHandler {
    @Autowired
    private CustomProvider customProvider;
    @Pointcut("@annotation(customKey)")
    public void customKeyPointCut(CustomKey customKey) {}

    @Pointcut("@annotation(customFieldKey)")
    public void customFieldKeyPointCut(CustomFieldKey customFieldKey) {}

    @Around("customKeyPointCut(customKey)")
    public Object customKeyAround(ProceedingJoinPoint joinPoint, CustomKey customKey) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String fKey = customProvider.getParameterKey(joinPoint);
        Object[] args = joinPoint.getArgs();
        args[0] = fKey;
        String key = customProvider.handleCustomKey(customKey, signature.getMethod(), joinPoint.getArgs());
        String resolveExpression =  customProvider.handleSpelExpression(customKey);
        log.info("Spel解析之后的key为：{}", resolveExpression);
        log.info("处理后的key为： {}", key);
        return joinPoint.proceed(args);
    }

    @Around("customFieldKeyPointCut(customFieldKey)")
    public Object customFieldKeyAround(ProceedingJoinPoint joinPoint, CustomFieldKey customFieldKey) throws Throwable {
//        String key = customProvider.handleCustomFieldKey(customFieldKey, joinPoint, 0);
//        Object[] args = joinPoint.getArgs();
//        args[0] = key;
        return joinPoint.proceed();
    }
}
