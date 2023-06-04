package com.vigacat.security.dao.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.vigacat.security.dao.entity"})
@EnableJpaRepositories(basePackages = {"com.vigacat.security.dao.repository"})
public class VigacatSecurityDaoConfig {
}
