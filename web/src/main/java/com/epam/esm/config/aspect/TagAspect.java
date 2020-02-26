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
public class TagAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String CORRELATION_ID = "correlationId";

    @Before("com.epam.esm.config.aspect.PointCutConfig.findMostPopularTag()")
    public void logBeforeFindMostPopularTag(JoinPoint joinPoint) {
        ThreadContext.put(CORRELATION_ID, UUID.randomUUID().toString());
        logger.info("Start {}", joinPoint.getSignature());
    }

    @AfterReturning("com.epam.esm.config.aspect.PointCutConfig.findMostPopularTag()")
    public void logAfterFindMostPopularTag(JoinPoint joinPoint) {
        ThreadContext.remove(CORRELATION_ID);
        logger.info("Ended {} successfully", joinPoint.getSignature());
    }

    @AfterThrowing("com.epam.esm.config.aspect.PointCutConfig.findMostPopularTag()")
    public void logAfterFindMostPopularTagThrowException(JoinPoint joinPoint) {
        ThreadContext.remove(CORRELATION_ID);
        logger.info("The {} failed", joinPoint.getSignature());
    }
}