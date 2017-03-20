package ru.okpdmarket.fake;

import lombok.val;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.okpdmarket.dao.ClassificatorDao;
import ru.okpdmarket.dao.dto.ClassificatorDaoDto;
import ru.okpdmarket.dao.dto.ClassificatorItemDaoDto;
import ru.okpdmarket.dao.dto.ClassificatorLinkDaoDto;

import java.util.ArrayList;
import java.util.Arrays;

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
        classificator.setCode("okpd");
        classificator.setName("ОКПД");
        classificator.setDescription("тестовый ОКПД");
        classificator.setTree(new ArrayList<>());

        ClassificatorItemDaoDto item1 = new ClassificatorItemDaoDto();
        item1.setCode("1");
        item1.setName("Животные");
        item1.setNotes("Категория с животными");
        item1.setChildren(new ArrayList<>());
        classificator.getTree().add(item1);

        ClassificatorItemDaoDto item11 = new ClassificatorItemDaoDto();
        item11.setCode("11");
        item11.setName("Собаки");
        item11.setNotes("тестовые собаки");
        item1.getChildren().add(item11);

        ClassificatorItemDaoDto item12 = new ClassificatorItemDaoDto();
        item12.setCode("12");
        item12.setName("Кошки");
        item12.setNotes("Сиамские кошки");
        item12.setChildren(new ArrayList<>());
        item1.getChildren().add(item12);

        ClassificatorItemDaoDto item121 = new ClassificatorItemDaoDto();
        item121.setCode("121");
        item121.setName("Кошечки(женский пол)");
        item121.setNotes("Киски");
        item121.setChildren(new ArrayList<>());
        item12.getChildren().add(item121);

        ClassificatorItemDaoDto item1211 = new ClassificatorItemDaoDto();
        item1211.setCode("1211");
        item1211.setName("Голубоглазые ");
        item1211.setNotes("Голубоглазые сиамские кошки");
        item1211.setChildren(new ArrayList<>());
        item1211.getChildren().add(item1211);

        ClassificatorItemDaoDto item2 = new ClassificatorItemDaoDto();
        item2.setCode("2");
        item2.setName("Техника");
        item2.setNotes("Тестовая техника");
        item2.setChildren(new ArrayList<>());
        classificator.getTree().add(item2);

        ClassificatorDaoDto classificator2 = new ClassificatorDaoDto();
        classificator2.setCode("tnvd");
        classificator2.setName("ТНВД");
        classificator2.setDescription("тестовый ТНВД");
        classificator2.setTree(new ArrayList<>());

        ClassificatorItemDaoDto itemT1 = new ClassificatorItemDaoDto();
        itemT1.setCode("1");
        itemT1.setName("Кошки");
        itemT1.setNotes("Категория с кошками");
        itemT1.setChildren(new ArrayList<>());
        classificator2.getTree().add(itemT1);

        val link1 = new ClassificatorLinkDaoDto(classificator.getCode(), item11.getCode(), classificator2.getCode(), itemT1.getCode());
        val link2 = new ClassificatorLinkDaoDto(classificator.getCode(), item12.getCode(), classificator2.getCode(), itemT1.getCode());
        val linksList = Arrays.asList(link1, link2);
        classificator.setLinks(linksList);

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
