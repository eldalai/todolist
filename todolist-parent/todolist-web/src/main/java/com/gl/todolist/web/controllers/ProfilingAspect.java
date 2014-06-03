package com.gl.todolist.web.controllers;

import static com.codahale.metrics.MetricRegistry.name;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

@Component
@Aspect
public class ProfilingAspect {

	@Autowired
	private MetricRegistry registry;
	 
	
	
	public ProfilingAspect() {
		super();
	}

	@Pointcut("execution(public * com.gl.todolist.web.controllers.*Controller.*(..))")
	public void pointcut() {
	}

	
	@Around("pointcut()")
	public Object measurePerformance(ProceedingJoinPoint joinpoint) throws Throwable {
		String timerName = joinpoint.getSignature().toShortString();
		Timer timer = this.registry.timer(timerName);
		Timer.Context context = timer.time();
		
		try {
			return joinpoint.proceed();
		}
		finally {
			context.stop();
		}
	}
	
	
	
//	@Before("performance()")
//	public void greetPerformer() {
//		System.out.println("ObjectiveJudge -> Before -> Hello!");
//	}
//
//	@AfterReturning("performance()")
//	public void judgePerformance() {
//		System.out.println("ObjectiveJudge -> AfterReturning -> It was fine");
//	}
//
//	@AfterThrowing("performance()")
//	public void commentOnBadPerformance() {
//		System.out
//				.println("ObjectiveJudge -> AfterThrowing --> That wasn't good!");
//	}

	
	
	
}
