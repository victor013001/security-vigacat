package com.vigacat.security.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.vigacat.security.service.component"
})
public class VigacatSecurityServiceConfig {
}
