package com.vigacat.security.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.vigacat.security.web.controller"
})

public class VigacatSecurityWebConfig {
}
