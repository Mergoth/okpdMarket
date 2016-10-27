package ru.okpdmarket.repository.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.okpdmarket.dao.FakeDaoImpl;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.repository.ClassificatorRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lalka on 10/9/2016.
 */
@Service
public class ClassificatorRepositoryImpl implements ClassificatorRepository {

    private final FakeDaoImpl fakeDao;

    private List<Classificator> classificatorList;

    @Autowired
    public ClassificatorRepositoryImpl(FakeDaoImpl fakeDao) {
        this.fakeDao = fakeDao;
    }

    @PostConstruct
    private void init() {
        classificatorList = new ArrayList<>(fakeDao.getAll());
    }

    @Override
    public List<Classificator> getClassificators() {
        return classificatorList;
    }

    @Override
    public void updateClassificators(List<Classificator> classificators) {
        this.classificatorList = new ArrayList<>(classificators);
    }
}
