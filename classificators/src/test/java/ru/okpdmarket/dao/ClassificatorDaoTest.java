package ru.okpdmarket.dao;

import org.junit.Before;
import org.junit.Rule;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;
import ru.okpdmarket.model.Classificator;

import java.util.Collections;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

/**
 * Created by User on 25.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ClassificatorDaoTest {

    @Rule
    public JUnitRestDocumentation restDocumentation =
            new JUnitRestDocumentation("build/generated-snippets");
    @Autowired
    ClassificatorDao classificatorDao;

    @Autowired
    private WebApplicationContext context;

    @Test
    public void addClassificator(){
        Classificator classificator = new Classificator("code", "test");
        classificator.add("1", "Test");
        classificator.add("11", "TestLevel11", "1");
        classificator.add("12", "TestLevel12", "1");
        classificator.add("13", "TestLevel13", "1");
        classificator.add("121", "TestLevel121", "12");
        classificator.add("2", "Test2");
        classificatorDao.save(classificator);
    }

    @Test
    public void getAllClassificators(){
        assertNotNull(classificatorDao.findAll());
    }

    @Test
    public void findById(){
        Classificator classificator = new Classificator("code", "test");
        classificator.setId("test");
        classificatorDao.save(classificator);
        Classificator classificator1 = classificatorDao.findOne("test");
        assertEquals(classificator.getCode(), classificator1.getCode());
    }


}
