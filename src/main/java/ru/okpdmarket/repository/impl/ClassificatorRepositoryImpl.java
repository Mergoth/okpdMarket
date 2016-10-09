package ru.okpdmarket.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.okpdmarket.dao.impl.GenericDaoImpl;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.repository.ClassificatorRepository;

import java.util.List;

/**
 * Created by lalka on 10/9/2016.
 */
@Service
public class ClassificatorRepositoryImpl  implements ClassificatorRepository{

    GenericDaoImpl genericDao;

    List<Classificator> classificatorList;

    ClassificatorRepositoryImpl(){
        genericDao = new GenericDaoImpl(Classificator.class, "classificator");
        classificatorList = genericDao.getAll();
    }

    @Override
    public List<Classificator> getClassificators() {
        return classificatorList;
    }

    @Override
    public List<Classificator> updateClassificators() {
        classificatorList = genericDao.getAll();
        return classificatorList;
    }
}
