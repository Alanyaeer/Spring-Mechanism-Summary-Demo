package com.learn.spring.aop;

import com.learn.spring.aop.monitor.HelloWorld;
import com.learn.spring.aop.monitor.TestAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class Pointcuts {
	// 切点为 AopTestAdviceTest 的所有方法
	@Pointcut("execution(* com.learn.spring.aop.monitor.AopTestAdviceTest.*(..))")
	public void executionMethod() {}

	@Pointcut("@annotation(testAnnotation) && args(helloWorld)")
	public void annotationAdvice(TestAnnotation testAnnotation, HelloWorld helloWorld) {}

	@Pointcut("bean(tradeService)")
	public void beanAdvice() {

	}

	@Pointcut("within(com.learn.spring.aop.monitor.*) || within(com.learn.spring.aop.monitor2.*)")
	public void withInMethod(){}

//	@Around("executionMethod()")
//	public Object aroundPublicMethod(ProceedingJoinPoint pjp) throws Throwable {
//		pjp.getSignature();
//        return pjp.proceed();
//	}


//	@Before("withInMethod()")
//	public Object beforeWithInMethod(JoinPoint jp) throws Throwable {
//		jp.getSignature();
//		log.info(jp.getArgs().toString());
//		return null;
//	}

//	@Around("beanAdvice()")
//	public Object aroundBeanAdvice(ProceedingJoinPoint pjp) throws Throwable {
//		Object target = pjp.getTarget();
//		log.info(target.toString());
//		pjp.getSignature();
//		return pjp.proceed();
//	}
//
	@Around("annotationAdvice(testAnnotation, helloWorld)")
	public Object aroundAnnotationAdvice(ProceedingJoinPoint pjp, TestAnnotation testAnnotation, HelloWorld helloWorld) throws Throwable {
		pjp.getSignature();
		Object[] args = pjp.getArgs();
		log.info("around Annotation : param: {}", testAnnotation.value());
		log.info("around Annotation : method signature: {}", pjp.getSignature());
		log.info("Hello World Info: {}", helloWorld);
		pjp.getTarget();
		return pjp.proceed();
	}


}