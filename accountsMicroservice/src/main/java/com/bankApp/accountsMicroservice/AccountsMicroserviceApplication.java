package com.bankApp.accountsMicroservice;

import com.bankApp.accountsMicroservice.dtos.AccountsContactInfoDTO;
import com.bankApp.accountsMicroservice.entity.Customer;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(value= AccountsContactInfoDTO.class)
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Account Microservice Rest API documentation.",
				description = "EazyBank Accounts micorservice REST API Docs",
				version = "1.0",
				contact = @Contact(
						name = "Yassine ben kacem",
						email = "yassinbenkacem12@gmail.com",
						url = "https://www.yassinebenkacem.ma"
				)
		)
)
public class AccountsMicroserviceApplication {

	public static void main(String[] args) {

		SpringApplication.run(AccountsMicroserviceApplication.class, args);
		//
		Customer customer = new Customer();

	}

}
