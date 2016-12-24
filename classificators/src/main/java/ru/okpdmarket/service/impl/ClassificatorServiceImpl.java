package ru.okpdmarket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.okpdmarket.dao.ClassificatorDao;
import ru.okpdmarket.dao.ClassificatorItemDao;
import ru.okpdmarket.dao.dto.ClassificatorDaoDto;
import ru.okpdmarket.dto.ClassificatorTypeDto;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.repository.ClassificatorRepository;
import ru.okpdmarket.service.ClassificatorService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladislav on 04.09.2016.
 */
@Component
public class ClassificatorServiceImpl implements ClassificatorService {
    @Autowired
    ClassificatorRepository repository;

    @Autowired
    ClassificatorDao classificatorDao;

    @Autowired
    ClassificatorItemDao classificatorItemDao;

    @Override
    public List<ClassificatorTypeDto> getClassificatorTypes() {
        return ClassificatorTypeDto.Converter.toDtoList(new ArrayList<>(repository.getClassificators().values()));
    }

    @Override
    public Classificator getClassifiactor(String classificatorCode) {
        return repository.getClassificators().get(classificatorCode);
    }

    @Override
    public void commitClassificators(List<Classificator> classificators) {
        for(Classificator classificator : classificators){
            ClassificatorDaoDto daoDto = ClassificatorDaoDto.Converter.toDto(classificator);
            classificatorDao.save(daoDto);
        }
    }
}
