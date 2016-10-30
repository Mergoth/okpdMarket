package ru.okpdmarket.service;

import ru.okpdmarket.dto.ClassificatorTypeDto;
import ru.okpdmarket.model.Classificator;

import java.util.List;

/**
 * Created by Vladislav on 04.09.2016.
 */
public interface ClassificatorService {

    List<ClassificatorTypeDto> getClassificatorTypes();

    Classificator getClassifiactor(String classificatorCode);



}
