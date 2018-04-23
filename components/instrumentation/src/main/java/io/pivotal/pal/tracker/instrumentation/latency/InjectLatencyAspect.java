package io.pivotal.pal.tracker.instrumentation.latency;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class InjectLatencyAspect {
    private final LatencyCmd latencyCmd;

    public InjectLatencyAspect(LatencyCmd latencyCmd) {
        this.latencyCmd = latencyCmd;
    }

    @Around("@annotation(io.pivotal.pal.tracker.instrumentation.latency.InstrumentLatency)")
    public Object invokeFailure(ProceedingJoinPoint joinPoint)
            throws Throwable {

        latencyCmd.execute();

        return joinPoint.proceed();
    }
}
