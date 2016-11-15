package ru.okpdmarket;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * Created by Igor_Usachev on 11/9/2016.
 */
@Configuration
@PropertySource(value = {"classpath:mongodb.properties"})
public class MongoConfig extends AbstractMongoConfiguration {

    @Autowired
    private Environment environment;

    @Override
    protected String getDatabaseName() {
        return environment.getProperty("mongodb.dbName");
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(environment.getProperty("mongodb.host"), Integer.parseInt(environment.getProperty("mongodb.port")));
    }

    @Override
    protected String getMappingBasePackage() {
        return "ru.okdp";
    }
}