package com.vigacat.security.persistence.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {
        "com.vigacat.security.persistence.component"
})
@Import(ModelMapperConfiguration.class)
public class VigacatPersistenceConfiguration {
}
