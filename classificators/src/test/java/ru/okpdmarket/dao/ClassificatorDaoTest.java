package ru.okpdmarket.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.okpdmarket.dao.dto.ClassificatorDaoDto;
import ru.okpdmarket.dao.dto.ClassificatorItemDaoDto;
import ru.okpdmarket.model.Classificator;

import static org.junit.Assert.assertTrue;

/**
 * Created by User on 25.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ClassificatorDaoTest {

    @Autowired
    ClassificatorDao classificatorDao;


    @Test
    public void addClassificator(){
        ClassificatorDaoDto classificator = new ClassificatorDaoDto();
        classificator.setCode("code");
        classificator.setName("testName");

        ClassificatorItemDaoDto iDD1 = new ClassificatorItemDaoDto();
        iDD1.setName("testItemName");
        iDD1.setCode("1");

        ClassificatorItemDaoDto iDD2 = new ClassificatorItemDaoDto();
        iDD2.setName("testItemName2");
        iDD2.setCode("2");

        ClassificatorItemDaoDto iDD11 = new ClassificatorItemDaoDto();
        iDD11.setName("testItemName11");
        iDD11.setCode("11");

        ClassificatorItemDaoDto iDD111 = new ClassificatorItemDaoDto();
        iDD111.setName("testItemName111");
        iDD111.setCode("111");

        iDD11.getChildren().add(iDD111);
        iDD1.getChildren().add(iDD11);

        ClassificatorItemDaoDto iDD12 = new ClassificatorItemDaoDto();
        iDD12.setName("testItemName12");
        iDD12.setCode("12");
        iDD1.getChildren().add(iDD12);

        classificator.getTree().add(iDD1);
        classificator.getTree().add(iDD2);

        classificatorDao.save(classificator);
        assertTrue(true);
    }

    @Test
    public void getAllClassificators(){
        // assertNotNull(classificatorDao.findAll());
    }

    @Test
    public void findById(){
        Classificator classificator = new Classificator();
        //"code", "test"
        classificator.setCode("test");
        // TODO: fix test
        // classificatorDao.save(classificator);
        //Classificator classificator1 = classificatorDao.findOne("test");
        //assertEquals(classificator.getCode(), classificator1.getCode());
    }


}
