package ru.okpdmarket.service.impl;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.okpdmarket.dao.ClassificatorDao;
import ru.okpdmarket.dao.DaoSerializer;
import ru.okpdmarket.dao.dto.ClassificatorDaoDto;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.repository.ClassificatorRepository;
import ru.okpdmarket.service.ClassificatorService;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Vladislav on 04.09.2016.
 */
@Component
public class ClassificatorServiceImpl implements ClassificatorService {
    private final ClassificatorRepository repository;

    private final ClassificatorDao classificatorDao;

    private final DaoSerializer daoSerializer;

    @Autowired
    public ClassificatorServiceImpl(ClassificatorRepository repository, ClassificatorDao classificatorDao, @Lazy DaoSerializer daoSerializer) {
        this.repository = repository;
        this.classificatorDao = classificatorDao;
        this.daoSerializer = daoSerializer;
    }

    @PostConstruct
    private void init() {
        List<ClassificatorDaoDto> classificatorDaoAll = (List<ClassificatorDaoDto>) classificatorDao.findAll();
        List<Classificator> classificators = daoSerializer.deserializeList(classificatorDaoAll);
        classificators.forEach(repository::putClassificator);
    }

    @Override
    public List<Classificator> getClassificatorTypes() {
        return repository.getClassificators();
    }

    @Override
    public List<ClassificatorItem> getClassificatorFirstLevel(String classificatorCode) {
        return repository.getClassificatorByCode(classificatorCode).getContents().getFirstLevel();
    }

    @Override
    public void commitClassificators() {
        val classificators = repository.getClassificators();

        val daoDtos = daoSerializer.serializeList(classificators);
        for (ClassificatorDaoDto daoDto : daoDtos) {
            classificatorDao.save(daoDto);
        }
    }

    @Override
    public List<Classificator> put(Classificator classificator) {
        repository.putClassificator(classificator);
        return repository.getClassificators();
    }


    @Override
    public ClassificatorItem getItem(String classificatorCode, String itemCode) {
        return repository.getClassificatorByCode(classificatorCode).getContents().getItemByCode(itemCode);
    }
}
