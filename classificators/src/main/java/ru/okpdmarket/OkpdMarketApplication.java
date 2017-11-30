package ru.okpdmarket;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static java.util.Collections.singletonList;

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
                        HttpMethod.OPTIONS.name(), HttpMethod.PUT.name());
            }
        };
    }

    @Configuration
    public class ApplicationContextEventTestsAppConfig extends AbstractMongoConfiguration {
        @Value("${spring.data.mongodb.host:localhost}")
        private String host;
        @Value("${spring.data.mongodb.port:0}")
        private Integer port;
        @Value("${spring.data.mongodb.username:}")
        private String username;
        @Value("${spring.data.mongodb.database:}")
        private String database;
        @Value("${spring.data.mongodb.password:}")
        private String password;

        @Override
        public String getDatabaseName() {
            return database;
        }

        @Bean
        public Mongo mongo() {
            if (username.isEmpty() && password.isEmpty()) {
                return new MongoClient(singletonList(new ServerAddress(host, port)));
            } else {
                return new MongoClient(singletonList(new ServerAddress(host, port)),
                        singletonList(MongoCredential.createCredential(username, database, password.toCharArray())));
            }

        }
    }
}
