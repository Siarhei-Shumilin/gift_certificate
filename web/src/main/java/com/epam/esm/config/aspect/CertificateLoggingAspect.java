package com.epam.esm.config.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
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
        LOGGER.info("Start searching certificates" + joinPoint.getSignature().getName());
    }

    @After("findByParameters()")
    public void logAfterFindCertificates(JoinPoint joinPoint) {
        LOGGER.info("End searching certificates" + joinPoint.getSignature().getName());
    }

    @Before("delete()")
    public void logBeforeDeleteCertificate(JoinPoint joinPoint) {
        LOGGER.info("Start delete certificates" + joinPoint.getSignature().getName());
    }

    @After("delete()")
    public void logAfterDeleteCertificates(JoinPoint joinPoint) {
        LOGGER.info("End delete certificates" + joinPoint.getSignature().getName());
    }

    @Before("save()")
    public void logBeforeSaveCertificate(JoinPoint joinPoint) {
        LOGGER.info("Start save certificates" + joinPoint.getSignature().getName());
    }

    @After("save()")
    public void logAfterSaveCertificates(JoinPoint joinPoint) {
        LOGGER.info("End save certificates" + joinPoint.getSignature().getName());
    }
}