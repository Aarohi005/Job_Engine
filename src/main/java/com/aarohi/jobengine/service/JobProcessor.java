package com.aarohi.jobengine.service;

import com.aarohi.jobengine.model.Job;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class JobProcessor {

    private final long processingDelayMs;
    private final double failureProbability;

    public JobProcessor(
            @Value("${job.processing-delay-ms:100}") long processingDelayMs,
            @Value("${job.failure-probability:0.1}") double failureProbability) {
        if (processingDelayMs < 0 || failureProbability < 0 || failureProbability > 1) {
            throw new IllegalArgumentException("Invalid job processor configuration");
        }
        this.processingDelayMs = processingDelayMs;
        this.failureProbability = failureProbability;
    }

    public String process(Job job) throws InterruptedException {
        Thread.sleep(processingDelayMs);
        if (job.getPayload().toLowerCase().contains("fail")
                || ThreadLocalRandom.current().nextDouble() < failureProbability) {
            throw new IllegalStateException("Job processing failed");
        }
        return "Processed: " + job.getPayload();
    }
}
