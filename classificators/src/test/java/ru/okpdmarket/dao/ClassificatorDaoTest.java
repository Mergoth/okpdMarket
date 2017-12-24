package ru.okpdmarket.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.okpdmarket.IntegrationTest;
import ru.okpdmarket.dao.dto.ClassificatorDaoDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ClassificatorDaoTest extends IntegrationTest {

    @Autowired
    ClassificatorDao classificatorDao;

    @Test
    public void addClassificator(){
        ClassificatorDaoDto classificator = new ClassificatorDaoDto();
        classificator.setCode("code");
        classificator.setName("testName");
        classificator.setDescription("testDescription");
        classificatorDao.save(classificator);
        assertTrue(true);
    }

    @Test
    public void getAllClassificators(){
        assertNotNull(classificatorDao.findAll());
    }

    @Test
    public void findById(){
        ClassificatorDaoDto classificator = new ClassificatorDaoDto();
        //"code", "test"
        classificator.setCode("test");
        classificatorDao.save(classificator);
        ClassificatorDaoDto classificator1 = classificatorDao.findOne("test");
        assertEquals(classificator.getCode(), classificator1.getCode());
    }

}
