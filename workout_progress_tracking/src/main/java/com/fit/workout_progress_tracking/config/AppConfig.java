//package com.fit.workout_progress_tracking.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Configuration
//    public class AppConfig {
//
//        private static final String BEARER_TOKEN = "YOUR_TOKEN_HERE"; // You can dynamically inject this if needed
//
//        @Bean
//        public RestTemplate restClientTemplate() {
//            RestTemplate restTemplate = new RestTemplate();
//
//            // Create interceptor to add Bearer token
//            ClientHttpRequestInterceptor bearerInterceptor = (request, body, execution) -> {
//                request.getHeaders().add("Authorization", "Bearer " + BEARER_TOKEN);
//                return execution.execute(request, body);
//            };
//
//            List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(restTemplate.getInterceptors());
//            interceptors.add(bearerInterceptor);
//            restTemplate.setInterceptors(interceptors);
//
//            return restTemplate;
//        }
//    }
//}
//}
