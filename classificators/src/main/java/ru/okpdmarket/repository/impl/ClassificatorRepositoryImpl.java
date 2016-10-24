package ru.okpdmarket.repository.impl;


import org.springframework.stereotype.Service;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.repository.ClassificatorRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lalka on 10/9/2016.
 */
@Service
public class ClassificatorRepositoryImpl implements ClassificatorRepository {



    List<Classificator> classificatorList;

    ClassificatorRepositoryImpl(){
            classificatorList = new ArrayList<>();
    }

    @Override
    public List<Classificator> getClassificators() {
        return classificatorList;
    }

    @Override
    public void updateClassificators(List<Classificator> classificators) {
        List<Classificator> newClassificators = new ArrayList<>(classificators);
        this.classificatorList = newClassificators;
    }
}
