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
public class PurchaseAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("com.epam.esm.config.aspect.PointCutConfig.findUsersPurchases()")
    public void logBeforeFindUsersPurchases(JoinPoint joinPoint) {
        ThreadContext.put("correlationId", UUID.randomUUID().toString());
        logger.info("Start " + joinPoint.getSignature());
    }

    @AfterReturning("com.epam.esm.config.aspect.PointCutConfig.findUsersPurchases()")
    public void logAfterFindUsersPurchases(JoinPoint joinPoint) {
        ThreadContext.remove("correlationId");
        logger.info("Ended " + joinPoint.getSignature() + " successfully");
    }

    @AfterThrowing("com.epam.esm.config.aspect.PointCutConfig.findUsersPurchases()")
    public void logAfterFindUsersPurchasesThrowException(JoinPoint joinPoint) {
        ThreadContext.remove("correlationId");
        logger.info("The " + joinPoint.getSignature() + " failed");
    }
}
