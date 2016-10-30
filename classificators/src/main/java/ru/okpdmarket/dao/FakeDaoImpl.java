package ru.okpdmarket.dao;

import org.springframework.stereotype.Repository;
import ru.okpdmarket.model.Classificator;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Vladislav on 26.10.2016.
 */
@Repository
public class FakeDaoImpl implements GenericDao<Classificator,UUID> {
    @Override
    public void remove(Classificator entity) {

    }

    @Override
    public Classificator getById(Object rowKey) {
        return null;
    }

    @Override
    public List<Classificator> getByNativeQuery(String s) {
        return null;
    }

    @Override
    public List<Classificator> getAll() {
        Classificator classificator1 = new Classificator("okpd", "ОКПД");
        classificator1.add("01.","Death");
        classificator1.add("01.01.","Napalm Death","01.");
        return Collections.singletonList(classificator1);
    }

    @Override
    public void save(Object object) {

    }

    @Override
    public void removeById(Object rowKey) {

    }
}
