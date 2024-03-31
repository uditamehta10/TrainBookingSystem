package com.example.trainbookingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class TrainBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainBookingSystemApplication.class, args);
	}

}
