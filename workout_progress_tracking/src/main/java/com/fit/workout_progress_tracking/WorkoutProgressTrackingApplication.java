package com.fit.workout_progress_tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@SpringBootApplication
public class WorkoutProgressTrackingApplication {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // Interceptor to forward Authorization header
        restTemplate.getInterceptors().add((request, body, execution) -> {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                String authHeader = attrs.getRequest().getHeader("Authorization");
                if (authHeader != null) {
                    request.getHeaders().add("Authorization", authHeader);
                }
            }
            return execution.execute(request, body);
        });

        return restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(WorkoutProgressTrackingApplication.class, args);
        System.out.println("Workout Progress Tracking Application Started ALREADY "  );
    }
}
