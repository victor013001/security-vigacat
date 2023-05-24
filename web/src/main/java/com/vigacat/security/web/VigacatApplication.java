package com.vigacat.security.web;

import com.vigacat.security.persistence.configuration.VigacatPersistenceConfiguration;
import com.vigacat.security.service.config.VigacatSecurityServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.vigacat.security.web.controller"
})
@Import({VigacatSecurityServiceConfig.class, VigacatPersistenceConfiguration.class})
public class VigacatApplication {

    public static void main(String... args) {
        SpringApplication.run(VigacatApplication.class, args);
    }

}
