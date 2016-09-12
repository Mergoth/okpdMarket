package ru.okpdmarket.services;

import ru.okpdmarket.model.Classificator;

import java.util.List;

/**
 * Created by Vladislav on 04.09.2016.
 */
public interface ClassificatorService {

    List<String> getClassificatorTypes();

    Classificator getClassifiactor(String classificatorId);



}
