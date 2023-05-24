package com.vigacat.security.persistence.configuration;

import org.hibernate.proxy.HibernateProxy;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(
                mappingContext -> {
                    if(mappingContext.getSource() instanceof HibernateProxy && ((HibernateProxy)mappingContext.getSource()).getHibernateLazyInitializer().isUninitialized()) {
                        return false;
                    }
                    return true;
                }
        );
        return new ModelMapper();
    }

}
