package com.jobtracker.DataManagement.controller;


import com.jobtracker.DataManagement.model.jobApplication;
import com.jobtracker.DataManagement.service.jobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*") // Allow frontend access
public class JobController {

    private final jobService JobService;

    public JobController(jobService JobService) {
        System.out.println("in controller line 20");
        this.JobService = JobService;
        System.out.println("in controller line 22");
    }

    @PostMapping("/upload")
    public ResponseEntity<jobApplication> uploadJob(
            @RequestParam String company,
            @RequestParam String role,
            @RequestParam String location,
            @RequestParam String jobId,
            @RequestParam String url,
            @RequestParam(required = false) MultipartFile resume
    ) {
        try {
            System.out.println("in controller line 35");
            jobApplication job = JobService.saveJob(company, role, location, jobId, url, resume);
            System.out.println("in controller line 37");
            return ResponseEntity.ok(job);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<jobApplication>> getAllJobs() {
        System.out.println("in controller line 46");
        return ResponseEntity.ok(JobService.getAllJobs());
    }
}

