package com.example.CLP_Case_Study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ClpCaseStudyApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ClpCaseStudyApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ClpCaseStudyApplication.class, args);
	}

}
