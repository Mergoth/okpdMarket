package ru.okpdmarket.service.impl;

import lombok.val;
import org.springframework.stereotype.Component;
import ru.okpdmarket.dao.ClassificatorDao;
import ru.okpdmarket.dao.DaoSerializer;
import ru.okpdmarket.dao.dto.ClassificatorDaoDto;
import ru.okpdmarket.dao.dto.ClassificatorItemDaoDto;
import ru.okpdmarket.dao.dto.ClassificatorLinkDaoDto;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.repository.ClassificatorRepository;
import ru.okpdmarket.service.ClassificatorService;
import ru.okpdmarket.service.SearchService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vladislav on 04.09.2016.
 */
@Component
public class ClassificatorServiceImpl implements ClassificatorService {
    private final ClassificatorRepository repository;

    private final ClassificatorDao classificatorDao;

    private final DaoSerializer daoSerializer;

    private final SearchService searchService;

    private final ClassificatorItemService itemService;

    public ClassificatorServiceImpl(ClassificatorRepository repository,
                                    ClassificatorDao classificatorDao,
                                    DaoSerializer daoSerializer,
                                    SearchService searchService,
                                    ClassificatorItemService itemService) {
        this.repository = repository;
        this.classificatorDao = classificatorDao;
        this.daoSerializer = daoSerializer;
        this.searchService = searchService;
        this.itemService = itemService;
    }

    @PostConstruct
    private void init() {
        List<ClassificatorDaoDto> classificatorDaoAll = (List<ClassificatorDaoDto>) classificatorDao.findAll();
        Loader loader = new Loader();
        List<Classificator> classificators = loader.deserializeList(classificatorDaoAll);
        classificators.forEach(repository::putClassificator);
        classificators.forEach(searchService::indexClassificator);
        classificatorDaoAll.forEach(d -> loader.loadLinks(d.getLinks()));
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

    private class Loader {
        public List<Classificator> deserializeList(List<ClassificatorDaoDto> dtos) {
            return dtos.stream().map(this::deserialize).collect(Collectors.toList());
        }

        public Classificator deserialize(ClassificatorDaoDto dto) {
            Classificator classificator = new Classificator();
            classificator.setCode(dto.getCode());
            classificator.setName(dto.getName());
            classificator.setDescription(dto.getDescription());
            put(classificator);
            loadChildren(null, dto.getTree(), classificator);
            return classificator;
        }

        private void loadChildren(String parentCode, List<ClassificatorItemDaoDto> fromDtoList, Classificator targetClassificator) {
            for (ClassificatorItemDaoDto childDto : fromDtoList) {
                val item = new ClassificatorItem(childDto.getCode(), childDto.getName(), childDto.getNotes());
                item.setParentCode(parentCode);
                itemService.addItem(targetClassificator.getCode(), item);

                if (!childDto.getChildren().isEmpty()) {
                    loadChildren(childDto.getCode(), childDto.getChildren(), targetClassificator);
                }
            }
        }

        public void loadLinks(List<ClassificatorLinkDaoDto> links) {
            if (links != null)
                links.forEach(this::loadLink);
        }

        private void loadLink(ClassificatorLinkDaoDto link) {
            val sourceItem = getItem(link.getSrcClsCode(), link.getSrcItemCode());
            val targetItem = getItem(link.getDstClsCode(), link.getDstItemCode());
            itemService.linkItem(sourceItem, targetItem);
        }
    }
}
