package ru.okpdmarket.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.okpdmarket.model.Classificator;

import java.util.List;

/**
 * Created by lalka on 10/26/2016.
 */
@EnableMongoRepositories(basePackages = "ru.okpdmarket.dao")
public interface ClassificatorDao extends MongoRepository<Classificator, String> {

}
