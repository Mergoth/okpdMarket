package ru.okpdmarket.service.impl;

import org.springframework.stereotype.Service;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.service.ClassificatorUpdateService;

import java.util.List;

/**
 * Created by Vladislav on 02.11.2016.
 */
@Service
public class ClassificatorUpdateServiceImpl implements ClassificatorUpdateService {
    @Override
    public List<Classificator> put(Classificator classificator) {
        return null;
    }

    @Override
    public List<ClassificatorItem> putItem(ClassificatorItem item) {
        return null;
    }
}
