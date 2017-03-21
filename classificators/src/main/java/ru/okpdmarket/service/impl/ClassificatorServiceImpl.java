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
import ru.okpdmarket.service.SearchService;

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

    private final SearchService searchService;

    @Autowired
    public ClassificatorServiceImpl(ClassificatorRepository repository, ClassificatorDao classificatorDao, @Lazy DaoSerializer daoSerializer, SearchService searchService) {
        this.repository = repository;
        this.classificatorDao = classificatorDao;
        this.daoSerializer = daoSerializer;
        this.searchService = searchService;
    }

    @PostConstruct
    private void init() {
        List<ClassificatorDaoDto> classificatorDaoAll = (List<ClassificatorDaoDto>) classificatorDao.findAll();
        List<Classificator> classificators = daoSerializer.deserializeList(classificatorDaoAll);
        classificators.forEach(repository::putClassificator);
        classificators.forEach(searchService::indexClassificator);
        classificatorDaoAll.forEach(d -> daoSerializer.loadLinks(d.getLinks()));
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
        classificatorDao.save(daoDtos);
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
