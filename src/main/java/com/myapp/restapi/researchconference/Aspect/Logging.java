package com.myapp.restapi.researchconference.Aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class Logging {
    private final Log logger = LogFactory.getLog(getClass());

    @Before("execution(* com.myapp.restapi.researchconference.*.*(..))")
    public void logMethodCall(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();

//        logger.debug("Method  : " + methodName + "Called on " + className);
        System.out.println("Method  : " + methodName + "Called on " + className);
    }
}
