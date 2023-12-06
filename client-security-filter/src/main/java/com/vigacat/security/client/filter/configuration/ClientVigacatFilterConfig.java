package com.vigacat.security.client.filter.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan(basePackages = {"com.vigacat.security.client.filter.component"})
public class ClientVigacatFilterConfig {

    @Value("${security.url}")
    private String securityUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(securityUrl.concat("/vigacat-security"))
                .build();
    }
}
