package ru.okpdmarket.fake;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.okpdmarket.dao.ClassificatorDao;
import ru.okpdmarket.dao.dto.ClassificatorDaoDto;
import ru.okpdmarket.dao.dto.ClassificatorItemDaoDto;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by vladislav on 09/12/2016.
 */
@Profile("debug")
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
        classificator.setCode("code");
        classificator.setName("test");
        classificator.setDescription("Test descr");
        classificator.setId("1234324");
        classificator.setTree(new ArrayList<>());

        ClassificatorItemDaoDto item1 = new ClassificatorItemDaoDto();
        item1.setCode("1");
        item1.setName("Test");
        item1.setNotes("Test1 notes");
        item1.setChildren(new ArrayList<>());
        classificator.getTree().add(item1);

        ClassificatorItemDaoDto item11 = new ClassificatorItemDaoDto();
        item11.setCode("11");
        item11.setName("TestLevel11");
        item11.setNotes("Test11 notes");
        item1.getChildren().add(item11);

        ClassificatorItemDaoDto item12 = new ClassificatorItemDaoDto();
        item12.setCode("12");
        item12.setName("TestLevel12");
        item12.setNotes("Test12 notes");
        item12.setChildren(new ArrayList<>());
        item1.getChildren().add(item12);


        ClassificatorItemDaoDto item121 = new ClassificatorItemDaoDto();
        item121.setCode("121");
        item121.setName("TestLevel121");
        item121.setNotes("Test121 notes");
        item121.setChildren(new ArrayList<>());
        item12.getChildren().add(item121);

        ClassificatorItemDaoDto item2 = new ClassificatorItemDaoDto();
        item2.setCode("2");
        item2.setName("Test2");
        item2.setNotes("Test2 notes");
        item2.setChildren(new ArrayList<>());
        classificator.getTree().add(item2);

        return Collections.singletonList(classificator);
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
