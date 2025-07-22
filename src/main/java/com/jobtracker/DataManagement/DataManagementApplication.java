package com.jobtracker.DataManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.jobtracker")
public class DataManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataManagementApplication.class, args);
	}

}
