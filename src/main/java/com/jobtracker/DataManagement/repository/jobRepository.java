package com.jobtracker.DataManagement.repository;
import com.jobtracker.DataManagement.model.jobApplication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface jobRepository extends MongoRepository<jobApplication, String> {
    List<jobApplication> findByUserId(String userId);
}