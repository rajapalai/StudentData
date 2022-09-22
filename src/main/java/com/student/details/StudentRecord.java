package com.student.details;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "student api",version = "3.0",description = "Student microservice"))
public class StudentRecord {

	public static void main(String[] args) {
		SpringApplication.run(StudentRecord.class, args);
	}

}
