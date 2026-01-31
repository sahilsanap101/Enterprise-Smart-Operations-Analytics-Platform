package com.enterprise.ops.backend.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.enterprise.ops.backend..*Controller.*(..))")
    public Object logApiCalls(ProceedingJoinPoint joinPoint) throws Throwable {

        String method = joinPoint.getSignature().toShortString();
        log.info("API CALL START → {}", method);

        Object result = joinPoint.proceed();

        log.info("API CALL END → {}", method);
        return result;
    }
}