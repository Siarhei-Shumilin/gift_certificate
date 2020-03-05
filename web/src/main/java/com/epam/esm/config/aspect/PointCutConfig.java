package com.epam.esm.config.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointCutConfig {
    /**
     * Monitoring pointcut.
     */
    @Pointcut("execution(* com.epam.esm.controller.*.*(..))")
    public void monitor() {
        // Do nothing because pointcut just specifies where exactly to apply advice
    }

    @Pointcut("execution(* com.epam.esm.controller.*.*(..))")
    public void anyControllerMethod() {
        // Do nothing because pointcut just specifies where exactly to apply advice
    }
}