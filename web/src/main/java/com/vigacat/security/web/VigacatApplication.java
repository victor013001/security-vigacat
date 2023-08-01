package com.vigacat.security.web;

import com.vigacat.security.dao.config.VigacatSecurityDaoConfig;
import com.vigacat.security.filterRequestVigacat.configuration.VigacatFilterRequestConfig;
import com.vigacat.security.persistence.configuration.VigacatPersistenceConfiguration;
import com.vigacat.security.service.config.VigacatSecurityServiceConfig;
import com.vigacat.security.web.config.VigacatSecurityWebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        VigacatSecurityServiceConfig.class,
        VigacatPersistenceConfiguration.class,
        VigacatSecurityDaoConfig.class,
        VigacatSecurityWebConfig.class,
        VigacatFilterRequestConfig.class
})
public class VigacatApplication {

    public static void main(String... args) {
        SpringApplication.run(VigacatApplication.class, args);
    }

}
