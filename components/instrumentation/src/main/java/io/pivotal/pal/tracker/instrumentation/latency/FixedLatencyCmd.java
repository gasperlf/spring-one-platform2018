package io.pivotal.pal.tracker.instrumentation.latency;

import static java.lang.Thread.sleep;

public class FixedLatencyCmd implements LatencyCmd {
    private final long latency;

    public FixedLatencyCmd(long latency) {
        this.latency = latency;
    }

    @Override
    public void execute() {
        try {
            sleep(latency);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
