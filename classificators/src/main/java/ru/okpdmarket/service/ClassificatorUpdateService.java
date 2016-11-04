package ru.okpdmarket.service;

import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;

import java.util.List;

/**
 * Created by Vladislav on 02.11.2016.
 */
public interface ClassificatorUpdateService {
    List<Classificator> put(Classificator classificator);

    List<ClassificatorItem> putItem(ClassificatorItem item);
}
