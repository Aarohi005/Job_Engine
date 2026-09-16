package com.aarohi.jobengine.controller;

import com.aarohi.jobengine.model.Job;
import com.aarohi.jobengine.model.JobRequest;
import com.aarohi.jobengine.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<Job> submit(@Valid @RequestBody JobRequest request) {
        Job job = jobService.create(request);
        return ResponseEntity.accepted()
                .location(URI.create("/jobs/" + job.getId()))
                .body(job);
    }

    @GetMapping("/{id}")
    public Job get(@PathVariable String id) {
        return jobService.get(id);
    }

    @GetMapping
    public List<Job> getAll() {
        return jobService.getAll();
    }
}
