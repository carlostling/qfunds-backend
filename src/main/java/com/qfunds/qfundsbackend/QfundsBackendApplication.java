package com.qfunds.qfundsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class QfundsBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(QfundsBackendApplication.class, args);
	}

}
