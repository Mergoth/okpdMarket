package ru.okpdmarket.service;

import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;

import java.util.List;


public interface ClassificatorService {

    List<Classificator> getClassificatorTypes();

    List<ClassificatorItem> getClassificatorFirstLevel(String classificatorCode);

    void commitClassificators();

    List<Classificator> put(Classificator classificator);

    ClassificatorItem getItem(String classificatorCode, String itemCode);
}
