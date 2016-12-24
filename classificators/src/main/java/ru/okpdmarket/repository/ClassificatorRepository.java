package ru.okpdmarket.repository;

import ru.okpdmarket.model.Classificator;

import java.util.List;
import java.util.Map;


public interface ClassificatorRepository {

    Map<String, Classificator> getClassificators();

    void updateClassificators(List<Classificator> classificators);

}
