package com.epam.esm.config.aspect;

import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class CertificateAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String CORRELATION_ID = "correlationId";

    @Before("com.epam.esm.config.aspect.PointCutConfig.update()")
    public void logBeforeUpdateCertificate(JoinPoint joinPoint) {
        ThreadContext.put(CORRELATION_ID, UUID.randomUUID().toString());
        logger.info("Start {}", joinPoint.getSignature());
    }

    @AfterReturning("com.epam.esm.config.aspect.PointCutConfig.update()")
    public void logAfterUpdateCertificates(JoinPoint joinPoint) {
        ThreadContext.remove(CORRELATION_ID);
        logger.info("Ended {} successfully", joinPoint.getSignature());
    }

    @AfterThrowing("com.epam.esm.config.aspect.PointCutConfig.update()")
    public void logAfterUpdateThrowException(JoinPoint joinPoint) {
        ThreadContext.remove(CORRELATION_ID);
        logger.info("The {} failed", joinPoint.getSignature());
    }
}