package ru.okpdmarket.repository.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.okpdmarket.dao.FakeDaoImpl;
import ru.okpdmarket.dao.GenericDao;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.repository.ClassificatorRepository;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by lalka on 10/9/2016.
 */
@Service
public class ClassificatorRepositoryImpl implements ClassificatorRepository {

    private final GenericDao genericDao;

    private Map<String, Classificator> classificatorsMap;

    @Autowired
    public ClassificatorRepositoryImpl(GenericDao genericDao) {
        this.genericDao = genericDao;
    }

    @PostConstruct
    private void init() {
        updateClassificators(genericDao.getAll());
    }

    @Override
    public Map<String, Classificator> getClassificators() {
        return classificatorsMap;
    }

    @Override
    public void updateClassificators(List<Classificator> classificators) {
        classificatorsMap = classificators.stream().collect(Collectors.toMap(Classificator::getCode, Function.identity()));
    }
}
