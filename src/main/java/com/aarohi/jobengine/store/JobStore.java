package com.aarohi.jobengine.store;

import com.aarohi.jobengine.model.Job;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JobStore {

    private final ConcurrentHashMap<String, Job> jobs = new ConcurrentHashMap<>();

    public Job save(Job job) {
        jobs.put(job.getId(), job);
        return job;
    }

    public Optional<Job> findById(String id) {
        return Optional.ofNullable(jobs.get(id));
    }

    public Job update(Job job) {
        return save(job);
    }

    public List<Job> findAll() {
        List<Job> result = new ArrayList<>(jobs.values());
        result.sort(Comparator.comparingLong(Job::getCreatedAt));
        return result;
    }
}
