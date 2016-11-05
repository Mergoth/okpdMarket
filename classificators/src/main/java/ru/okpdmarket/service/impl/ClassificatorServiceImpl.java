package ru.okpdmarket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.okpdmarket.dto.ClassificatorTypeDto;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.repository.ClassificatorRepository;
import ru.okpdmarket.service.ClassificatorService;

import java.util.List;

/**
 * Created by Vladislav on 04.09.2016.
 */
@Component
public class ClassificatorServiceImpl implements ClassificatorService {
    @Autowired
    ClassificatorRepository repository;

    @Override
    public List<ClassificatorTypeDto> getClassificatorTypes() {
        return null;
    }

    @Override
    public Classificator getClassifiactor(String classificatorCode) {
        return repository.getClassificators().get(classificatorCode);
    }
}
