package cs544.fit.workout_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
                        // Everyone can GET
                        .requestMatchers(HttpMethod.GET, "/api/workouts/**").hasAnyAuthority("ROLE_USER", "ROLE_COACH", "ROLE_ADMIN")
                // Coaches and Admins can modify workouts
                .requestMatchers(HttpMethod.POST, "/api/workouts/**").hasAnyAuthority("ROLE_COACH", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/workouts/**").hasAnyAuthority("ROLE_COACH", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/workouts/**").hasAnyAuthority("ROLE_COACH", "ROLE_ADMIN")



                // Category endpoints
                .requestMatchers(HttpMethod.POST, "/api/categories/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/categories/**").hasAnyAuthority("ROLE_USER", "ROLE_COACH", "ROLE_ADMIN")

                // Anything else requires authentication
                .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}


