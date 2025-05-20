package com.fit.workout_progress_tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WorkoutProgressTrackingApplication {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(WorkoutProgressTrackingApplication.class, args);
        System.out.println("Workout Progress Tracking Application Started ALREADY "  );
    }
}
