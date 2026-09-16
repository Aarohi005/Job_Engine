package com.aarohi.jobengine.service;

import com.aarohi.jobengine.model.Job;
import com.aarohi.jobengine.model.JobRequest;
import com.aarohi.jobengine.queue.JobQueue;
import com.aarohi.jobengine.store.JobStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class JobService {

    private final JobStore jobStore;
    private final JobQueue jobQueue;

    public JobService(JobStore jobStore, JobQueue jobQueue) {
        this.jobStore = jobStore;
        this.jobQueue = jobQueue;
    }

    public Job create(JobRequest request) {
        Job job = new Job(UUID.randomUUID().toString(), request.getPayload(), request.getPriority());
        jobStore.save(job);
        jobQueue.enqueue(job);
        return job;
    }

    public Job get(String id) {
        return jobStore.findById(id)
                .orElseThrow(() -> new JobNotFoundException(id));
    }

    public List<Job> getAll() {
        return jobStore.findAll();
    }

    public static class JobNotFoundException extends RuntimeException {
        public JobNotFoundException(String id) {
            super("Job not found: " + id);
        }
    }
}
