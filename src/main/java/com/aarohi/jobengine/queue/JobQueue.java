package com.aarohi.jobengine.queue;

import com.aarohi.jobengine.model.Job;
import org.springframework.stereotype.Component;

import java.util.concurrent.PriorityBlockingQueue;

@Component
public class JobQueue {

    private final PriorityBlockingQueue<Job> queue = new PriorityBlockingQueue<>();

    public void enqueue(Job job) {
        queue.put(job);
    }

    public Job dequeue() throws InterruptedException {
        return queue.take();
    }

    public int size() {
        return queue.size();
    }
}
