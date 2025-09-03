package ru.top.diplom.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* ru.top.diplom.controller..*(..))")
    public void controllerLog() {
    }

    @Pointcut("execution(* ru.top.diplom.service..*(..))")
    public void serviceLog() {
    }

    @Before("controllerLog() || serviceLog()")
    public void beforeLog(JoinPoint joinPoint) {
        log.info("{}.{}() with args: {}",
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "controllerLog()", returning = "result")
    public void afterControllerLog(Object result) {
        if (result instanceof ResponseEntity<?> response) {
            Object body = response.getBody();
            log.info("Controller - Status: {} | Body: {}",
                    response.getStatusCode(),
                    body != null ? body : "null");
        } else {
            log.info("Controller - {}", result);
        }
    }

    @AfterReturning(pointcut = "serviceLog()", returning = "result")
    public void afterServiceLog(Object result) {
        log.info("Service - {}", result != null ? "Result: " + result : "Completed with no return value");
    }

    @AfterThrowing(pointcut = "controllerLog() || serviceLog()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.warn("Exception in {}.{}() => {}: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                ex.getClass().getSimpleName(),
                ex.getMessage());
    }
}