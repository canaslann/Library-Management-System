package com.libraryMs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Corsconfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // tüm endpointler
                        .allowedOrigins("http://127.0.0.1:5500") // izin verdiğin origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")// izin verilen metodlar
                        .allowedHeaders("*")
                        .allowCredentials(true); // cookie vb. izin
            }
        };
    }
}
