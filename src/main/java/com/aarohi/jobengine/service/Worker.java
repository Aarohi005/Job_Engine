package com.aarohi.jobengine.service;

import com.aarohi.jobengine.model.Job;
import com.aarohi.jobengine.model.JobStatus;
import com.aarohi.jobengine.queue.JobQueue;
import com.aarohi.jobengine.store.JobStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Worker implements Runnable {

    private static final int MAX_RETRIES = 3;
    private static final Logger log = LoggerFactory.getLogger(Worker.class);

    private final JobQueue jobQueue;
    private final JobStore jobStore;
    private final JobProcessor processor;

    public Worker(JobQueue jobQueue, JobStore jobStore, JobProcessor processor) {
        this.jobQueue = jobQueue;
        this.jobStore = jobStore;
        this.processor = processor;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Job job = jobQueue.dequeue();
                process(job);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void process(Job job) {
        job.setStatus(JobStatus.PROCESSING);
        jobStore.update(job);
        try {
            job.setResult(processor.process(job));
            job.setStatus(JobStatus.COMPLETED);
            jobStore.update(job);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            job.setStatus(JobStatus.RETRYING);
            jobStore.update(job);
        } catch (RuntimeException e) {
            int retries = job.getRetryCount() + 1;
            job.setRetryCount(retries);
            if (retries >= MAX_RETRIES) {
                job.setStatus(JobStatus.FAILED);
                log.warn("Job {} failed after {} retries", job.getId(), retries);
            } else {
                job.setStatus(JobStatus.RETRYING);
                jobQueue.enqueue(job);
            }
            jobStore.update(job);
        }
    }
}
