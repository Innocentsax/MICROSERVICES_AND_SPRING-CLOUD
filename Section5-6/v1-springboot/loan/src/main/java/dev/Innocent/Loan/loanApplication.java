package dev.Innocent.Loan;

import dev.Innocent.Loan.DTO.LoansContactInfoDTO;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties(value = {LoansContactInfoDTO.class})
@ComponentScans({@ComponentScan("dev.Innocent.Loan.Controller")})
@EnableJpaRepositories("dev.Innocent.Loan.Repository")
@EntityScan("dev.Innocent.Loan.Entity")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Loan Microservice REST API Documentation",
				description = "InnocentUdo's Bank Loan Microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Innocent Udo",
						email = "innocentcharlesudo@gmail.com",
						url = "https://innocentsax.netlify.app/"
				),
				license = @License(
						name = "MIT/Apache 2.0",
						url = "https://innocentsax.netlify.app/"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "InnocentUdo's Bank Loan Microservice REST API Documentation",
				url = "https://innocentsax.netlify.app/"
		)
)
public class loanApplication {

	public static void main(String[] args) {
		SpringApplication.run(loanApplication.class, args);
	}

}
