package com.aarohi.jobengine.config;

import com.aarohi.jobengine.queue.JobQueue;
import com.aarohi.jobengine.service.JobProcessor;
import com.aarohi.jobengine.service.Worker;
import com.aarohi.jobengine.store.JobStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class WorkerConfig {

    @Bean(destroyMethod = "shutdownNow")
    public ExecutorService workerExecutor(
            JobQueue jobQueue,
            JobStore jobStore,
            JobProcessor processor,
            @Value("${job.worker-count:4}") int workerCount) {
        if (workerCount < 1) {
            throw new IllegalArgumentException("job.worker-count must be at least 1");
        }
        ExecutorService executor = Executors.newFixedThreadPool(workerCount);
        for (int i = 0; i < workerCount; i++) {
            executor.submit(new Worker(jobQueue, jobStore, processor));
        }
        return executor;
    }
}
