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
    @Pointcut("execution(* com.epam.esm.controller.CertificateController.findByParameters(..))")
    public void findByParameters() {}

    @Pointcut("execution(* com.epam.esm.controller.CertificateController.delete(..))")
    public void delete() {}

    @Pointcut("execution(* com.epam.esm.controller.CertificateController.save(..))")
    public void save() {}

    @Before("findByParameters()")
    public void logBeforeFindCertificates(JoinPoint joinPoint) {
        LOGGER.info("Start " + joinPoint.getSignature().getName());
    }

    @AfterReturning("findByParameters()")
    public void logAfterFindCertificates(JoinPoint joinPoint) {
        LOGGER.info("Ended " + joinPoint.getSignature().getName() + " successfully");
    }

    @AfterThrowing("findByParameters()")
    public void logAfterFindCertificatesThrowException(JoinPoint joinPoint) {
        LOGGER.info("The " + joinPoint.getSignature().getName() + " failed");
    }

    @Before("delete()")
    public void logBeforeDeleteCertificate(JoinPoint joinPoint) {
        LOGGER.info("Start " + joinPoint.getSignature().getName() + " successfully");
    }

    @AfterReturning("delete()")
    public void logAfterDeleteCertificates(JoinPoint joinPoint) {
        LOGGER.info("Ended " + joinPoint.getSignature().getName() + " successfully");
    }

    @AfterThrowing("delete()")
    public void logAfterDeleteThrowException(JoinPoint joinPoint) {
        LOGGER.info("The " + joinPoint.getSignature().getName() + " failed");
    }

    @Before("save()")
    public void logBeforeSaveCertificate(JoinPoint joinPoint) {
        LOGGER.info("Start save certificates" + joinPoint.getSignature().getName());
    }

    @AfterReturning("save()")
    public void logAfterSaveCertificates(JoinPoint joinPoint) {
        LOGGER.info("Ended " + joinPoint.getSignature().getName() + " successfully");
    }

    @AfterThrowing("save()")
    public void logAfterSaveThrowException(JoinPoint joinPoint) {
        LOGGER.info("The " + joinPoint.getSignature().getName() + " failed");
    }
}