package ru.okpdmarket.service;

import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;

import java.util.List;

/**
 * Created by Vladislav on 04.09.2016.
 */
public interface ClassificatorService {

    List<Classificator> getClassificatorTypes();

    List<ClassificatorItem> getClassifiactorFirstLevel(String classificatorCode);

    void commitClassificators(List<Classificator> classificators);

    List<Classificator> put(Classificator classificator);

    List<ClassificatorItem> putItem(ClassificatorItem item);

    ClassificatorItem getItem(String classificatorId, String itemCode);
}
