package com.learn.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class Pointcuts {
	// 切点为 AopTestAdviceTest 的所有方法
	@Pointcut("execution(* com.learn.spring.aop.monitor.AopTestAdviceTest.*(..))")
	public void executionMethod() {}

	@Pointcut("@annotation(com.learn.spring.aop.monitor.TestAnnotation)")
	public void annotationAdvice() {}

	@Pointcut("bean(tradeServie)")
	public void beanAdvice() {

	}

	@Pointcut("within(com.learn.spring.aop.monitor.*) || within(com.learn.spring.aop.monitor2.*)")
	public void withInMethod(){}

	@Around("executionMethod()")
	public Object aroundPublicMethod(ProceedingJoinPoint pjp) throws Throwable {
		pjp.getSignature();
        return pjp.proceed();
	}

	//
	@Before("withInMethod()")
	public Object beforeWithInMethod(JoinPoint jp) throws Throwable {
		jp.getSignature();
		return null;
	}

	@Around("beanAdvice()")
	public Object aroundBeanAdvice(ProceedingJoinPoint pjp) throws Throwable {

		pjp.getSignature();
		return pjp.proceed();
	}

	@Around("annotationAdvice()")
	public Object aroundAnnotationAdvice(ProceedingJoinPoint pjp) throws Throwable {
		pjp.getSignature();
		Object[] args = pjp.getArgs();
		log.info("around Annotation param: {}", args[0]);
		pjp.getTarget();
		return pjp.proceed();
	}
}