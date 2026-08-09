package com.swiggy.swiggy_backend.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

    @Around("execution(* com.swiggy.swiggy_backend.service.impl.*.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        System.out.println("========== PERFORMANCE ==========");
        System.out.println("Class : "
                + joinPoint.getTarget().getClass().getSimpleName());
        System.out.println("Method : "
                + joinPoint.getSignature().getName());
        System.out.println("Execution Time : "
                + (end - start) + " ms");
        System.out.println("=================================");

        return result;
    }
}