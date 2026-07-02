package com.bankApp.loansMicroservice;

import com.bankApp.loansMicroservice.dtos.LoanContactInfoDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = LoanContactInfoDTO.class)
public class LoansMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansMicroserviceApplication.class, args);
	}

}
