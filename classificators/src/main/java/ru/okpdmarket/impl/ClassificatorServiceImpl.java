package ru.okpdmarket.impl;

import org.springframework.stereotype.Component;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.services.ClassificatorService;

import java.util.List;

/**
 * Created by Vladislav on 04.09.2016.
 */
@Component
public class ClassificatorServiceImpl implements ClassificatorService {
    @Override
    public List<String> getClassificatorTypes() {
        return null;
    }

    @Override
    public Classificator getClassifiactor(String classificatorId) {
        return null;
    }
}
