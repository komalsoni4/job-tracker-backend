

/*
package com.jobtracker.DataManagement.controller;


import com.jobtracker.DataManagement.model.jobApplication;
import com.jobtracker.DataManagement.service.jobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final jobService JobService;

    public JobController(jobService JobService) {
        System.out.println("in controller line 20");
        this.JobService = JobService;
        System.out.println("in controller line 22");
    }

    @PostMapping("/upload")
    public ResponseEntity<jobApplication> uploadJob(
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobId,
            @RequestParam(required = false) String url,
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

    @GetMapping("")
    public ResponseEntity<List<jobApplication>> getAllJobs() {
        System.out.println("in controller line 46");
        return ResponseEntity.ok(JobService.getAllJobs());
    }
}

 */

package com.jobtracker.DataManagement.controller;

import com.jobtracker.DataManagement.model.jobApplication;
import com.jobtracker.DataManagement.service.jobService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.jobtracker.DataManagement.model.User;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final jobService JobService;

    public JobController(jobService JobService) {
        this.JobService = JobService;
    }

    private String getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new IllegalStateException("User not authenticated.");
        }
        User user = (User) authentication.getPrincipal();
        return user.getId();
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
            String userId = getAuthenticatedUserId();
            jobApplication job = JobService.saveJob(userId, company, role, location, jobId, url, resume);
            return ResponseEntity.ok(job);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<jobApplication>> getAllJobs() {
        String userId = getAuthenticatedUserId();
        return ResponseEntity.ok(JobService.getJobsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<jobApplication> getJobById(@PathVariable String id) {
        String userId = getAuthenticatedUserId();
        return JobService.getJobByIdAndUserId(id, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<jobApplication> updateJob(
            @PathVariable String id,
            @RequestBody Map<String, Object> updates
    ) {
        try {
            String userId = getAuthenticatedUserId();
            jobApplication updatedJob = JobService.updateJob(id, userId, updates);
            return ResponseEntity.ok(updatedJob);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}