
/*
package com.jobtracker.DataManagement.service;

import com.jobtracker.DataManagement.model.jobApplication;
import com.jobtracker.DataManagement.repository.jobRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class jobService {

    private final jobRepository JobRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    public jobService(jobRepository JobRepository) {
        this.JobRepository = JobRepository;
    }

    public jobApplication saveJob(String company, String role, String location, String jobId, String url, MultipartFile resume) throws IOException {
        // Save resume file
        System.out.println("🛠️ Inside saveJob() line 29");
        String filename = null;
        if (resume != null && !resume.isEmpty()) {
            System.out.println("🛠️ Inside saveJob() line 32");
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                boolean created = uploadPath.mkdirs();  // <- this creates missing folders
                System.out.println("📁 Upload dir created: " + created);
            }
            filename = UUID.randomUUID() + "_" + resume.getOriginalFilename();
            System.out.println("🛠️ Inside saveJob() line 34");
            File dest = new File(uploadPath, filename);
            System.out.println("🛠️ Inside saveJob() line 36");
            System.out.println("Saving to path: " + dest.getAbsolutePath());
            System.out.println("🛠️ Inside saveJob() line 37");
            resume.transferTo(dest);
            System.out.println("🛠️ Inside saveJob() line 38");
        }

        // Save job data
        jobApplication job = new jobApplication();
        if (company != null) {
            job.setCompany(company);
        }
        if (role != null) {
            job.setRole(role);
        }
        if(location!=null)
        {
            job.setLocation(location);
        }
        if(jobId!=null)
        {
            job.setJobId(jobId);
        }
        if(url!=null)
        {
            job.setUrl(url);
        }

        job.setDateSaved(LocalDateTime.now());
        job.setResumeFilename(filename);
        System.out.println("🛠️ Inside saveJob() line 50");
        return JobRepository.save(job);
    }

    public List<jobApplication> getAllJobs() {
        return JobRepository.findAll();
    }
}

 */

package com.jobtracker.DataManagement.service;

import com.jobtracker.DataManagement.model.jobApplication;
import com.jobtracker.DataManagement.repository.jobRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class jobService {

    private final jobRepository JobRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    public jobService(jobRepository JobRepository) {
        this.JobRepository = JobRepository;
    }

    public jobApplication saveJob(String userId, String company, String role, String location, String jobId, String url, MultipartFile resume) throws IOException {
        String filename = null;
        if (resume != null && !resume.isEmpty()) {
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }
            filename = UUID.randomUUID() + "_" + resume.getOriginalFilename();
            File dest = new File(uploadPath, filename);
            resume.transferTo(dest);
        }

        jobApplication job = new jobApplication();
        job.setUserId(userId);
        job.setCompany(company);
        job.setRole(role);
        job.setLocation(location);
        job.setJobId(jobId);
        job.setUrl(url);
        job.setDateSaved(LocalDateTime.now());
        job.setResumeFilename(filename);
        job.setStatus("Applied");
        return JobRepository.save(job);
    }

    public List<jobApplication> getJobsByUserId(String userId) {
        return JobRepository.findByUserId(userId);
    }

    public Optional<jobApplication> getJobByIdAndUserId(String id, String userId) {
        return JobRepository.findById(id)
                .filter(job -> job.getUserId().equals(userId));
    }

    public jobApplication updateJob(String id, String userId, Map<String, Object> updates) {
        return JobRepository.findById(id).map(job -> {
            if (!job.getUserId().equals(userId)) {
                throw new IllegalArgumentException("Job not found or does not belong to the user.");
            }
            updates.forEach((key, value) -> {
                switch (key) {
                    case "company": job.setCompany((String) value); break;
                    case "role": job.setRole((String) value); break;
                    case "location": job.setLocation((String) value); break;
                    case "jobId": job.setJobId((String) value); break;
                    case "url": job.setUrl((String) value); break;
                    case "status": job.setStatus((String) value); break;
                    case "referralNotes": job.setReferralNotes((String) value); break;
                }
            });
            return JobRepository.save(job);
        }).orElseThrow(() -> new IllegalArgumentException("Job not found."));
    }
}
