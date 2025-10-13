package com.trendtube.processing_video_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class ProcessingVideoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessingVideoServiceApplication.class, args);
	}

}
