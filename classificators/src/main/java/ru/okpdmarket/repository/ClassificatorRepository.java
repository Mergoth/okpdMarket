package ru.okpdmarket.repository;

import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.repository.impl.ClassificatorContents;

import java.util.List;


public interface ClassificatorRepository {

    List<Classificator> getClassificators();

    ClassificatorContents getClassificatorContentsById(String id);

    ClassificatorContents putClassificator(Classificator classificator);
}
