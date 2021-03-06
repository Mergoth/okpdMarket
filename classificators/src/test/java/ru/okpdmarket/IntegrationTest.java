package ru.okpdmarket;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.containers.GenericContainer;

import static java.util.Collections.singletonList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {OkpdMarketApplication.class, IntegrationTest.ApplicationContextEventTestsAppConfig.class})
@WebAppConfiguration
public abstract class IntegrationTest {

    // Set up a redis container
    private static GenericContainer mongo =
            new GenericContainer("mongo:3.6.0-jessie")
                    .withExposedPorts(27017);

    static {
        mongo.start();
    }

    @Configuration
    public static class ApplicationContextEventTestsAppConfig extends AbstractMongoConfiguration {

        @Value("${spring.data.mongodb.database:}")
        private String database;

        @Override
        public String getDatabaseName() {
            return database;
        }

        @Bean
        public Mongo mongo() {
            return new MongoClient(singletonList(new ServerAddress(mongo.getContainerIpAddress(), mongo.getFirstMappedPort())));
        }
    }
}
