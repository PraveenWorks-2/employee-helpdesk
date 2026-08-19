package com.stackly.reimbursement.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReimbursementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run
				(ReimbursementServiceApplication.class, args);
	}

}
