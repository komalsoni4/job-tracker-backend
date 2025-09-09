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