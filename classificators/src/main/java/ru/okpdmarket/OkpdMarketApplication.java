package ru.okpdmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableConfigurationProperties
@EnableDiscoveryClient
@Configuration
public class OkpdMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(OkpdMarketApplication.class, args);
	}

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods(HttpMethod.GET.name(),
                        HttpMethod.HEAD.name(), HttpMethod.POST.name(),
                        HttpMethod.OPTIONS.name(), HttpMethod.PUT.name(),
                        HttpMethod.POST.name());
            }
        };
    }
}
