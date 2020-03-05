package com.epam.esm.config.aspect;

import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class GeneralCertificateTagAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String CORRELATION_ID = "correlationId";
    private static final String BEFORE = "Start {}";
    private static final String AFTER = "Ended {} successfully";
    private static final String FAILED = "The {} failed";

    @Before("com.epam.esm.config.aspect.PointCutConfig.anyControllerMethod()")
    public void logBeforeFind(JoinPoint joinPoint) {
        before(BEFORE, joinPoint);
    }

    @AfterReturning("com.epam.esm.config.aspect.PointCutConfig.anyControllerMethod()")
    public void logAfterFind(JoinPoint joinPoint) {
        after(AFTER, joinPoint);
    }

    @AfterThrowing("com.epam.esm.config.aspect.PointCutConfig.anyControllerMethod()")
    public void logAfterFindThrowException(JoinPoint joinPoint) {
        after(FAILED, joinPoint);
    }

    private void before(String message, JoinPoint joinPoint){
        ThreadContext.put(CORRELATION_ID, UUID.randomUUID().toString());
        logger.info(message, joinPoint.getSignature());
    }

    private void after(String message, JoinPoint joinPoint){
        ThreadContext.remove(CORRELATION_ID);
        logger.info(message, joinPoint.getSignature());
    }
}