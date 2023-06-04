package com.vigacat.security.persistence.configuration;

import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.collection.spi.PersistentList;
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
                    if(mappingContext.getSource() instanceof PersistentCollection && !((PersistentCollection)mappingContext.getSource()).wasInitialized()) {
                        return false;
                    }
                    if(mappingContext.getSource() instanceof PersistentList && !((PersistentList)mappingContext.getSource()).wasInitialized()) {
                        return false;
                    }
                    return true;
                }
        );
        return modelMapper;
    }

}
