package com.swiggy.swiggy_backend.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLoggingAspect {

    @AfterThrowing(
            pointcut = "execution(* com.swiggy.swiggy_backend.service.impl.*.*(..))",
            throwing = "exception")
    public void logException(
            JoinPoint joinPoint,
            Exception exception) {

        System.out.println("========== EXCEPTION ==========");

        System.out.println("Class : "
                + joinPoint.getTarget().getClass().getSimpleName());

        System.out.println("Method : "
                + joinPoint.getSignature().getName());

        System.out.println("Exception : "
                + exception.getClass().getSimpleName());

        System.out.println("Message : "
                + exception.getMessage());

        System.out.println("===============================");
    }
}