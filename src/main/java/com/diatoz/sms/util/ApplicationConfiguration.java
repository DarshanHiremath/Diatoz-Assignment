package com.diatoz.sms.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class ApplicationConfiguration {

	/*
	 * Spring Doc
	 */

	private Info info() {
		return new Info().title("Student-Management-System RESTful API documentation").description(
				"### The documentation provides the detailed information of a Student-Management-System RESTful service."
						+ " The API deals with User, Test and Test_Scores."
						+ " User will be able to register with either of Admin,Teacher or Student role."
						+ " <br>Tech-Stack - `Spring Boot` `Spring Data JPA` `MongoDB-Database` `Spring Security - for authorization`"
						+ " `JWT - for Authentication`.");
	}

	@Bean
	OpenAPI baseOpenAPI() {
		return new OpenAPI().info(info());
	}

	/*
	 * The Bean is used to map DTO object to corresponding Entity object and
	 * Visa-Versa
	 */
	@Bean
	ModelMapper getModelMapper() {
		return new ModelMapper();
	}

}
