package ru.okpdmarket;

import org.junit.Test;


public class OkpdMarketApplicationTests extends IntegrationTest {

   /* @Configuration
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
    }*/

    @Test
    public void contextLoads() {
    }

}
