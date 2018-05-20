package com.example.aop;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceMonitor{
	protected Log log = LogFactory.getLog(PerformanceMonitor.class);
	
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
	    try
	    {
	    	long startTime = new Date().getTime();
	        Object result = joinPoint.proceed();
	        log.warn("Comsuming " + (new Date().getTime() - startTime) + " ms　at " + joinPoint.getTarget().getClass() + "." + signature.getName());
	        return result;
	    }
	    catch (IllegalArgumentException e)
	    {
	    	log.warn("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in " + signature.getName() + "()");
	        throw e;
	    }
   }
}