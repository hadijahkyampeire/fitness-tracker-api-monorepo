package cs544.fit.user_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableJpaAuditing
@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "User Service API",
		version = "1.0",
		description = "API for user authentication and profile management"
))
@SecurityScheme(
		name = "BearerAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT"
)
public class UserServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserServiceApplication.class, args);
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		System.out.println("Hadijah (user123): " + encoder.encode("user123"));
//		System.out.println("Mariam (coach123): " + encoder.encode("coach123"));
//		System.out.println("Jamal (admin123): " + encoder.encode("admin123"));
	}

}
