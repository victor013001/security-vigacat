package com.vigacat.security.filterRequestVigacat.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan(basePackages = {"com.vigacat.security.filterRequestVigacat.component"})
public class VigacatFilterRequestConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://127.0.0.1:8081/vigacat-security")
                .build();
    }
}
