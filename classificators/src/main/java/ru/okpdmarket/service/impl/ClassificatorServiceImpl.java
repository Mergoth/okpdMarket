package ru.okpdmarket.service.impl;

import lombok.val;
import org.springframework.stereotype.Component;
import ru.okpdmarket.dao.ClassificatorDao;
import ru.okpdmarket.dao.ClassificatorItemsDao;
import ru.okpdmarket.dao.ClassificatorLinksDao;
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

@Component
public class ClassificatorServiceImpl implements ClassificatorService {
    private final ClassificatorRepository repository;

    private final ClassificatorDao classificatorDao;

    private final DaoSerializer daoSerializer;

    private final SearchService searchService;

    private final ClassificatorItemService itemService;
    private final ClassificatorItemsDao classificatorItemsDao;
    private final Loader loader;
    private final ClassificatorLinksDao classificatorLinksDao;

    public ClassificatorServiceImpl(ClassificatorRepository repository,
                                    ClassificatorDao classificatorDao,
                                    ClassificatorItemsDao classificatorItemsDao,
                                    DaoSerializer daoSerializer,
                                    SearchService searchService,
                                    ClassificatorItemService itemService,
                                    ClassificatorLinksDao classificatorLinksDao) {
        this.repository = repository;
        this.classificatorDao = classificatorDao;
        this.classificatorItemsDao = classificatorItemsDao;
        this.daoSerializer = daoSerializer;
        this.searchService = searchService;
        this.itemService = itemService;
        this.classificatorLinksDao = classificatorLinksDao;
        this.loader = new Loader();
    }

    @PostConstruct
    private void init() {
        List<ClassificatorDaoDto> classificatorDaoAll = (List<ClassificatorDaoDto>) classificatorDao.findAll();

        List<Classificator> classificators = loader.deserializeList(classificatorDaoAll);
        classificators.forEach(this::loadClassificator);
    }

    private void loadClassificator(Classificator classificator) {
        repository.putClassificator(classificator);
        loader.loadItems(classificatorItemsDao.findByClassificatorCode(classificator.getCode()));
        searchService.indexClassificator(classificator);
        loader.loadLinks(classificatorLinksDao.findByClassificatorCode(classificator.getCode()));
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
        List<Classificator> classificators = repository.getClassificators();
        classificatorDao.deleteAll();
        classificatorItemsDao.deleteAll();
        classificatorLinksDao.deleteAll();
        classificators.forEach(c -> {
                    ClassificatorDaoDto classificatorDaoDto = daoSerializer.serialize(c);
                    classificatorDao.save(classificatorDaoDto);
                    List<ClassificatorItemDaoDto> itemDaoDtos = daoSerializer.serializeItems(c);
                    classificatorItemsDao.save(itemDaoDtos);
                    List<ClassificatorLinkDaoDto> linkDaoDtos = daoSerializer.serializeLinks(c);
                    classificatorLinksDao.save(linkDaoDtos);
                }
        );
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
            classificator.setDescription(dto.getDescription() != null ? dto.getDescription() : "");
            put(classificator);
            return classificator;
        }

        private void loadItem(ClassificatorItemDaoDto daoDto) {

            val item = new ClassificatorItem(daoDto.getCode(), daoDto.getName(), daoDto.getNotes());
            item.setParentCode(daoDto.getParentCode());
            itemService.addItem(daoDto.getClassificatorCode(), item);
        }

        public void loadLinks(List<ClassificatorLinkDaoDto> links) {
            if (links != null)
                links.forEach(this::loadLink);
        }

        private void loadLink(ClassificatorLinkDaoDto link) {
            val sourceItem = getItem(link.getClassificatorCode(), link.getItemCode());
            val targetItem = getItem(link.getDestinationClassificatorCode(), link.getDestinationItemCode());
            itemService.linkItem(sourceItem, targetItem);
        }

        public void loadItems(List<ClassificatorItemDaoDto> itemDaoDtos) {
            itemDaoDtos.forEach(this::loadItem);
        }
    }
}
