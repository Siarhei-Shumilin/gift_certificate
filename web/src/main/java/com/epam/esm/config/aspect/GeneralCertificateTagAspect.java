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
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Before("com.epam.esm.config.aspect.PointCutConfig.findByParameters()")
    public void logBeforeFind(JoinPoint joinPoint) {
        ThreadContext.put("correlationId", UUID.randomUUID().toString());
        LOGGER.info("Start " + joinPoint.getSignature());
    }

    @AfterReturning("com.epam.esm.config.aspect.PointCutConfig.findByParameters()")
    public void logAfterFind(JoinPoint joinPoint) {
        ThreadContext.remove("correlationId");
        LOGGER.info("Ended " + joinPoint.getSignature() + " successfully");
    }

    @AfterThrowing("com.epam.esm.config.aspect.PointCutConfig.findByParameters()")
    public void logAfterFindThrowException(JoinPoint joinPoint) {
        ThreadContext.remove("correlationId");
        LOGGER.info("The " + joinPoint.getSignature() + " failed");
    }

    @Before("com.epam.esm.config.aspect.PointCutConfig.delete()")
    public void logBeforeDelete(JoinPoint joinPoint) {
        ThreadContext.put("correlationId", UUID.randomUUID().toString());
        LOGGER.info("Start " + joinPoint.getSignature() + " successfully");
    }

    @AfterReturning("com.epam.esm.config.aspect.PointCutConfig.delete()")
    public void logAfterDelete(JoinPoint joinPoint) {
        ThreadContext.remove("correlationId");
        LOGGER.info("Ended " + joinPoint.getSignature() + " successfully");
    }

    @AfterThrowing("com.epam.esm.config.aspect.PointCutConfig.delete()")
    public void logAfterDeleteThrowException(JoinPoint joinPoint) {
        ThreadContext.remove("correlationId");
        LOGGER.info("The " + joinPoint.getSignature() + " failed");
    }

    @Before("com.epam.esm.config.aspect.PointCutConfig.save()")
    public void logBeforeSave(JoinPoint joinPoint) {
        ThreadContext.put("correlationId", UUID.randomUUID().toString());
        LOGGER.info("Start " + joinPoint.getSignature());
    }

    @AfterReturning("com.epam.esm.config.aspect.PointCutConfig.save()")
    public void logAfterSave(JoinPoint joinPoint) {
        ThreadContext.remove("correlationId");
        LOGGER.info("Ended " + joinPoint.getSignature() + " successfully");
    }

    @AfterThrowing("com.epam.esm.config.aspect.PointCutConfig.save()")
    public void logAfterSaveThrowException(JoinPoint joinPoint) {
        ThreadContext.remove("correlationId");
        LOGGER.info("The " + joinPoint.getSignature() + " failed");
    }
}
