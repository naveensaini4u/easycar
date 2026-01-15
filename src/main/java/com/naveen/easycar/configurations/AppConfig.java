package com.naveen.easycar.configurations;

import com.naveen.easycar.mapper.CarMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public CarMapper carMapper() {
        return new CarMapper();
    }
}
