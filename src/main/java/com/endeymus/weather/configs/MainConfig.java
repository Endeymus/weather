package com.endeymus.weather.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.endeymus.weather")
@PropertySource("classpath:prop/spring.properties")
public class MainConfig {
}
