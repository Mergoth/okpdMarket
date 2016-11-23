package ru.okpdmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableConfigurationProperties
@Configuration
public class OkpdMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(OkpdMarketApplication.class, args);
	}
}
