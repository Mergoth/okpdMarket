package ru.okpdmarket.fake;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.okpdmarket.dao.ClassificatorDao;
import ru.okpdmarket.model.Classificator;

import java.util.Collections;

/**
 * Created by vladislav on 09/12/2016.
 */
@Profile("debug")
@Repository
@Primary
public class FakeClassificatorDaoImpl implements ClassificatorDao {
    @Override
    public Classificator save(Classificator entity) {
        return null;
    }


    @Override
    public <S extends Classificator> Iterable<S> save(Iterable<S> entities) {
        return null;
    }

    @Override
    public Classificator findOne(String s) {
        return null;
    }

    @Override
    public boolean exists(String s) {
        return false;
    }

    @Override
    public Iterable<Classificator> findAll() {
        Classificator classificator = new Classificator("code", "test");
        classificator.add("1", "Test");
        classificator.add("11", "TestLevel11", "1");
        classificator.add("12", "TestLevel12", "1");
        classificator.add("13", "TestLevel13", "1");
        classificator.add("121", "TestLevel121", "12");
        classificator.add("2", "Test2");
        return Collections.singletonList(classificator);
    }

    @Override
    public Iterable<Classificator> findAll(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void delete(Classificator entity) {

    }

    @Override
    public void delete(Iterable<? extends Classificator> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
