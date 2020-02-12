package com.epam.esm.config.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointCutConfig {
    /**
     * Monitoring pointcut.
     */
    @Pointcut("execution(* com.epam.esm.controller.*.*(..))")
    public void monitor() {
    }
    @Pointcut("execution(* com.epam.esm.controller.CertificateController.findByParameters(..))")
    public void findByParameters() {}

    @Pointcut("execution(* com.epam.esm.controller.CertificateController.delete(..))")
    public void delete() {}

    @Pointcut("execution(* com.epam.esm.controller.CertificateController.save(..))")
    public void save() {}
}
