package ru.okpdmarket.service;

import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.model.dto.ClassificatorItemDto;

import java.util.List;

/**
 * Created by Vladislav on 04.09.2016.
 */
public interface ClassificatorService {

    List<ClassificatorTypeDto> getClassificatorTypes();

    Classificator getClassifiactor(String classificatorCode);

    void commitClassificators(List<Classificator> classificators);

    List<Classificator> put(ClassificatorTypeDto classificator);

    List<ClassificatorItem> putItem(ClassificatorItemDto item);
}
