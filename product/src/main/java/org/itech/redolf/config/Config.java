package org.itech.redolf.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ModelMapper modelmapper(){
        return new ModelMapper();
    }

    @Bean
    public ObjectMapper objectmapper(){
        return new ObjectMapper();
    }
}
