package ru.okpdmarket.service.impl;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.okpdmarket.dao.ClassificatorDao;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.repository.ClassificatorRepository;
import ru.okpdmarket.service.ClassificatorService;

import java.util.List;

/**
 * Created by Vladislav on 04.09.2016.
 */
@Component
public class ClassificatorServiceImpl implements ClassificatorService {
    final ClassificatorRepository repository;

    final ClassificatorDao classificatorDao;


    @Autowired
    public ClassificatorServiceImpl(ClassificatorRepository repository, ClassificatorDao classificatorDao) {
        this.repository = repository;
        this.classificatorDao = classificatorDao;
    }

    @Override
    public List<Classificator> getClassificatorTypes() {
        return repository.getClassificators();
    }

    @Override
    public List<ClassificatorItem> getClassificatorFirstLevel(String classificatorId) {
        return repository.getClassificatorContentsById(classificatorId).getFirstLevel();
    }

    @Override
    public void commitClassificators() {
        val classificators = getClassificatorTypes();

        //TODO: move this to another place
        //val daoDtos = serializer.serializeList(classificators);
       /* for(ClassificatorDaoDto daoDto : daoDtos){
            classificatorDao.save(daoDto);
        }*/
    }

    @Override
    public List<Classificator> put(Classificator classificator) {
        repository.putClassificator(classificator);
        return repository.getClassificators();
    }


    @Override
    public ClassificatorItem getItem(String classificatorId, String itemCode) {
        return repository.getClassificatorContentsById(classificatorId).getItemByCode(itemCode);
    }
}
