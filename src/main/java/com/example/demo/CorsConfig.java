package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@CrossOrigin(origins = {"http://localhost:8081","http://localhost:8080"})
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("CorsConfig: addCorsMapping metoda została wywołana");
        registry.addMapping("/api/v1/notes").allowedOrigins("*").allowedHeaders("*").allowedMethods("*");
    }
}

