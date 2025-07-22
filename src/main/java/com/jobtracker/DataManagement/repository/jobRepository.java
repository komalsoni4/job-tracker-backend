package com.jobtracker.DataManagement.repository;
import com.jobtracker.DataManagement.model.jobApplication;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface jobRepository extends MongoRepository<jobApplication, String> {
}
