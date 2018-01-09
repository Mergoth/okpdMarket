package ru.okpdmarket.fake;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.okpdmarket.dao.ClassificatorDao;
import ru.okpdmarket.dao.dto.ClassificatorDaoDto;

import java.util.Arrays;


@Profile("fakeDao")
@Repository
@Primary
public class FakeClassificatorDaoImpl implements ClassificatorDao {
    @Override
    public ClassificatorDaoDto save(ClassificatorDaoDto entity) {
        return null;
    }

    @Override
    public <S extends ClassificatorDaoDto> Iterable<S> save(Iterable<S> entities) {
        return null;
    }

    @Override
    public ClassificatorDaoDto findOne(String s) {
        return null;
    }

    @Override
    public boolean exists(String s) {
        return false;
    }

    @Override
    public Iterable<ClassificatorDaoDto> findAll() {
        ClassificatorDaoDto classificator = new ClassificatorDaoDto();
        classificator.setCode("okpd");
        classificator.setName("ОКПД");
        classificator.setDescription("тестовый ОКПД");

        ClassificatorDaoDto classificator2 = new ClassificatorDaoDto();
        classificator2.setCode("tnvd");
        classificator2.setName("ТНВД");
        classificator2.setDescription("тестовый ТНВД");

        return Arrays.asList(classificator, classificator2);
    }

    @Override
    public Iterable<ClassificatorDaoDto> findAll(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 1;
    }

    @Override
    public void delete(String s) {
    }

    @Override
    public void delete(ClassificatorDaoDto entity) {

    }

    @Override
    public void delete(Iterable<? extends ClassificatorDaoDto> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
