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

    @Pointcut("execution(* com.epam.esm.controller.*.findByParameters(..))")
    public void findByParameters() {
        // Do nothing because pointcut just specifies where exactly to apply advice
    }

    @Pointcut("execution(* com.epam.esm.controller.*.delete(..))")
    public void delete() {
        // Do nothing because pointcut just specifies where exactly to apply advice
    }

    @Pointcut("execution(* com.epam.esm.controller.*.save(..))")
    public void save() {
        // Do nothing because pointcut just specifies where exactly to apply advice
    }

    @Pointcut("execution(* com.epam.esm.controller.*.update(..))")
    public void update() {
        // Do nothing because pointcut just specifies where exactly to apply advice
    }

    @Pointcut("execution(* com.epam.esm.controller.*.findMostPopularTag(..))")
    public void findMostPopularTag() {
        // Do nothing because pointcut just specifies where exactly to apply advice
    }

    @Pointcut("execution(* com.epam.esm.controller.*.findUsersPurchases(..))")
    public void findUsersPurchases() {
        // Do nothing because pointcut just specifies where exactly to apply advice
    }
}