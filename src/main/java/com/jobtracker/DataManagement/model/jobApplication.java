package com.jobtracker.DataManagement.model;



import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "jobApplication")
public class jobApplication {
    @Id
    private String id;
    private String company;
    private String role;
    private String location;
    private String jobId;
    private String url;
    private LocalDateTime dateSaved;
    private String resumeFilename;
    private String userId;
    private String status;
    private String referralNotes;

    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public LocalDateTime getDateSaved() {
        return dateSaved;
    }
    public void setDateSaved(LocalDateTime dateSaved) {
        this.dateSaved = dateSaved;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getJobId() {
        return jobId;
    }
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getResumeFilename() {
        return resumeFilename;
    }
    public void setResumeFilename(String resumeFilename) {
        this.resumeFilename = resumeFilename;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getReferralNotes() {
        return referralNotes;
    }
    public void setReferralNotes(String referralNotes) {
        this.referralNotes = referralNotes;
    }
}

