package com.techsophy.awgment.taskmanagement;

import com.techsophy.awgment.taskmanagement.constants.TaskManagementConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static com.techsophy.awgment.taskmanagement.constants.TaskManagementConstants.CURRENT_PROJECT;

@RefreshScope
@EnableMongoRepositories
@EnableMongoAuditing
@SpringBootApplication
@ComponentScan({ CURRENT_PROJECT, TaskManagementConstants.MULTITENANCY_PROJECT})
public class TaskManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementApplication.class, args);
	}

}
