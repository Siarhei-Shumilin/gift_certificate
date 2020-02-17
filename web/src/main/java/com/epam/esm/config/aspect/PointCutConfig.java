package com.epam.esm.config.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointCutConfig {
    /**
     * Monitoring pointcut.
     */
    @Pointcut("execution(* com.epam.esm.controller.*.*(..))")
    public void monitor() {
    }

    @Pointcut("execution(* com.epam.esm.controller.*.findByParameters(..))")
    public void findByParameters() {}

    @Pointcut("execution(* com.epam.esm.controller.*.delete(..))")
    public void delete() {}

    @Pointcut("execution(* com.epam.esm.controller.*.save(..))")
    public void save() {}

    @Pointcut("execution(* com.epam.esm.controller.*.update(..))")
    public void update() {}

    @Pointcut("execution(* com.epam.esm.controller.*.findMostPopularTag(..))")
    public void findMostPopularTag() {}

    @Pointcut("execution(* com.epam.esm.controller.*.findUsersPurchases(..))")
    public void findUsersPurchases() {}
}