package com.aarohi.jobengine.model;

import java.time.Instant;
import java.util.Objects;

public class Job implements Comparable<Job> {

    private final String id;
    private JobStatus status;
    private int priority;
    private int retryCount;
    private String payload;
    private String result;
    private final long createdAt;

    public Job(String id, String payload, int priority) {
        this.id = id;
        this.payload = payload;
        this.priority = priority;
        this.status = JobStatus.PENDING;
        this.retryCount = 0;
        this.createdAt = Instant.now().toEpochMilli();
    }

    // 🔁 Priority logic (higher priority first)
    @Override
    public int compareTo(Job other) {
        return Integer.compare(other.priority, this.priority);
    }

    // Getters

    public String getId() {
        return id;
    }

    public JobStatus getStatus() {
        return status;
    }

    public int getPriority() {
        return priority;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public String getPayload() {
        return payload;
    }

    public String getResult() {
        return result;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    // Setters (controlled mutability)

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void setResult(String result) {
        this.result = result;
    }

    // Optional: equality based on ID (important for maps/sets)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Job)) return false;
        Job job = (Job) o;
        return id.equals(job.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Debugging / logging

    @Override
    public String toString() {
        return "Job{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", retryCount=" + retryCount +
                ", payload='" + payload + '\'' +
                ", result='" + result + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}