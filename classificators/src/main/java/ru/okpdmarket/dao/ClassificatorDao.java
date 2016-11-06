package ru.okpdmarket.dao;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import ru.okpdmarket.model.Classificator;

import java.util.List;

/**
 * Created by lalka on 10/26/2016.
 */
public interface ClassificatorDao extends CassandraRepository<Classificator> {

    @Query("SELECT*FROM classificators")
    List<Classificator> getClassificators();
}
