package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin(origins = {"http://localhost:8081","http://localhost:8080"})
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DemoApplication {
    public static void main(String[] args) {
        
        SpringApplication.run(DemoApplication.class, args);
    }
}
