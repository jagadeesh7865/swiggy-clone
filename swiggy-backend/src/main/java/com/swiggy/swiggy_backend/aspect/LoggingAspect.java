package com.swiggy.swiggy_backend.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.swiggy.swiggy_backend.service.impl.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {

        System.out.println("========== METHOD START ==========");

        System.out.println("Class : "
                + joinPoint.getTarget().getClass().getSimpleName());

        System.out.println("Method : "
                + joinPoint.getSignature().getName());

        System.out.println("Arguments : "
                + Arrays.toString(joinPoint.getArgs()));

        System.out.println("==================================");
    }
}