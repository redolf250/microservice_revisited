package org.itech.redolf.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Value("${address.base.url}")
    private String baseUrl;

    @Bean
    public ModelMapper modelmapper(){
        return new ModelMapper();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public WebClient webClient(){
        return WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

}
