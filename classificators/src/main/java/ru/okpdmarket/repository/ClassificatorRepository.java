package ru.okpdmarket.repository;

import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;

import java.util.List;


public interface ClassificatorRepository {

    List<Classificator> getClassificators();

    List<ClassificatorItem> getClassificatorContents(String id);

    void putClassificator(Classificator classificator);
}
