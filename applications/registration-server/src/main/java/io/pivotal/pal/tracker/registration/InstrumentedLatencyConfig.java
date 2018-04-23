package io.pivotal.pal.tracker.registration;

import io.pivotal.pal.tracker.instrumentation.latency.FixedLatencyCmd;
import io.pivotal.pal.tracker.instrumentation.latency.InjectLatencyAspect;
import io.pivotal.pal.tracker.instrumentation.latency.LatencyCmd;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstrumentedLatencyConfig {
    private final long latency;

    public InstrumentedLatencyConfig(
            @Value("${registration.server.latency.ms}") long latency) {
        this.latency = latency;
    }

    @Bean
    public LatencyCmd latencyCmd() {
        return new FixedLatencyCmd(latency);
    }

    @Bean
    public InjectLatencyAspect injectLatencyAspect() {
        return new InjectLatencyAspect(latencyCmd());
    }
}
