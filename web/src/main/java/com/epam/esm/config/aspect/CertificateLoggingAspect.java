package com.epam.esm.config.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CertificateLoggingAspect {

private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Before("com.epam.esm.config.aspect.PointCutConfig.findByParameters()")
    public void logBeforeFindCertificates(JoinPoint joinPoint) {
        LOGGER.info("Start " + joinPoint.getSignature().getName());
    }

    @AfterReturning("com.epam.esm.config.aspect.PointCutConfig.findByParameters()")
    public void logAfterFindCertificates(JoinPoint joinPoint) {
        LOGGER.info("Ended " + joinPoint.getSignature().getName() + " successfully");
    }

    @AfterThrowing("com.epam.esm.config.aspect.PointCutConfig.findByParameters()")
    public void logAfterFindCertificatesThrowException(JoinPoint joinPoint) {
        LOGGER.info("The " + joinPoint.getSignature().getName() + " failed");
    }

    @Before("com.epam.esm.config.aspect.PointCutConfig.delete()")
    public void logBeforeDeleteCertificate(JoinPoint joinPoint) {
        LOGGER.info("Start " + joinPoint.getSignature().getName() + " successfully");
    }

    @AfterReturning("com.epam.esm.config.aspect.PointCutConfig.delete()")
    public void logAfterDeleteCertificates(JoinPoint joinPoint) {
        LOGGER.info("Ended " + joinPoint.getSignature().getName() + " successfully");
    }

    @AfterThrowing("com.epam.esm.config.aspect.PointCutConfig.delete()")
    public void logAfterDeleteThrowException(JoinPoint joinPoint) {
        LOGGER.info("The " + joinPoint.getSignature().getName() + " failed");
    }

    @Before("com.epam.esm.config.aspect.PointCutConfig.save()")
    public void logBeforeSaveCertificate(JoinPoint joinPoint) {
        LOGGER.info("Start save certificates" + joinPoint.getSignature().getName());
    }

    @AfterReturning("com.epam.esm.config.aspect.PointCutConfig.save()")
    public void logAfterSaveCertificates(JoinPoint joinPoint) {
        LOGGER.info("Ended " + joinPoint.getSignature().getName() + " successfully");
    }

    @AfterThrowing("com.epam.esm.config.aspect.PointCutConfig.save()")
    public void logAfterSaveThrowException(JoinPoint joinPoint) {
        LOGGER.info("The " + joinPoint.getSignature().getName() + " failed");
    }
}