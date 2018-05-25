package com.example.aop;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceMonitor {
	protected Log log = LogFactory.getLog(PerformanceMonitor.class);

	@Before("execution(* com.example.controller..*(..))")
	public void beforeController(JoinPoint joinPoint) throws Throwable {
		System.out.println("validate in controller");
	}

	@After("execution(* com.example.controller..*(..))")
	public void afterController(JoinPoint joinPoint) throws Throwable {
		System.out.println("after controller");
	}

	@AfterReturning("execution(* com.example.controller..*(..))")
	public void fterControllerReturning(JoinPoint joinPoint) throws Throwable {
		System.out.println("after controller return");
	}

	@AfterThrowing("execution(* com.example.controller..*(..))")
	public void fterControllerThrowing(JoinPoint joinPoint) throws Throwable {
		System.out.println("after controller throwing");
	}

	@Around("execution(* com.example.controller..*(..))")
	public Object collectControllerExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		return collectExecutionTime(joinPoint);
	}

	@Around("execution(* com.example.dao..*(..))")
	public Object collectDaoExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		return collectExecutionTime(joinPoint);
	}

	@Around("execution(* com.example.service..*(..))")
	public Object collectServiceExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		return collectExecutionTime(joinPoint);
	}

	protected Object collectExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		Signature signature = joinPoint.getSignature();
		try {
			long startTime = new Date().getTime();
			Object result = joinPoint.proceed();
			log.warn("Comsuming " + (new Date().getTime() - startTime) + " ms　at " + joinPoint.getTarget().getClass()
					+ "." + signature.getName());
			return result;
		} catch (IllegalArgumentException e) {
			log.warn("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in " + signature.getName() + "()");
			throw e;
		}
	}
}