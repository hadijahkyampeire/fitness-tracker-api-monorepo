package cs544.fit.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableJpaAuditing
@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserServiceApplication.class, args);
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		System.out.println("Hadijah (user123): " + encoder.encode("user123"));
//		System.out.println("Mariam (coach123): " + encoder.encode("coach123"));
//		System.out.println("Jamal (admin123): " + encoder.encode("admin123"));
	}

}
