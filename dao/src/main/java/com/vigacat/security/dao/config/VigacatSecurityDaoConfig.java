package com.vigacat.security.dao.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"com.vigacat.security.dao.entity"})
public class VigacatSecurityDaoConfig {
}
