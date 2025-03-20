package com.zomind.testcase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


@SpringBootApplication
@Slf4j
public class TestcaseApplication {

	public static void main(String[] args ) {
		SpringApplication.run(TestcaseApplication.class, args);

		System.out.println("\n\nRunning...");
	}

}
