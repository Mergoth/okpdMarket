package ru.okpdmarket.dao;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.okpdmarket.dao.dto.ClassificatorDaoDto;
import ru.okpdmarket.dao.dto.ClassificatorItemDaoDto;
import ru.okpdmarket.dao.dto.ClassificatorLinkDaoDto;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.service.ClassificatorService;
import ru.okpdmarket.service.impl.ClassificatorItemService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by vladislav on 09/01/2017.
 */
@Service
public class DaoSerializer {

    private final ClassificatorService service;
    private final ClassificatorItemService itemService;

    @Autowired
    public DaoSerializer(ClassificatorService service, ClassificatorItemService itemService) {
        this.service = service;
        this.itemService = itemService;
    }

    private ClassificatorItemDaoDto serialize(ClassificatorItem item) {
        val dto = new ClassificatorItemDaoDto(item);

        // Serialize children
        val childrenItems = item.getRelations().getChildren();
        val fullChildrenList = childrenItems.stream().map(this::serialize).collect(Collectors.toList());
        dto.setChildren(fullChildrenList);
        return dto;
    }

    public List<ClassificatorItemDaoDto> serializeList(Collection<ClassificatorItem> items) {
        return items.stream().map(this::serialize).collect(Collectors.toList());
    }

    private ClassificatorDaoDto serialize(Classificator item) {
        ClassificatorDaoDto dto = new ClassificatorDaoDto();
        dto.setCode(item.getCode());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        val daoDtoChilderList = serializeList(item.getContents().getFirstLevel());
        dto.setTree(daoDtoChilderList);
        dto.setLinks(serializeLinks(item));
        return dto;
    }

    public List<ClassificatorDaoDto> serializeList(List<Classificator> items) {
        return items.stream().map(this::serialize).collect(Collectors.toList());
    }

    public Classificator deserialize(ClassificatorDaoDto dto) {
        Classificator classificator = new Classificator();
        classificator.setCode(dto.getCode());
        classificator.setName(dto.getName());
        classificator.setDescription(dto.getDescription());
        service.put(classificator);
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

    public List<Classificator> deserializeList(List<ClassificatorDaoDto> dtos) {
        return dtos.stream().map(this::deserialize).collect(Collectors.toList());
    }


    public List<ClassificatorLinkDaoDto> serializeLinks(Classificator classificator) {
        return classificator.getContents().getElements().values().stream()
                .flatMap(this::extractItemLinks)
                .collect(Collectors.toList());
    }

    private Stream<ClassificatorLinkDaoDto> extractItemLinks(ClassificatorItem item) {

        return item.getRelations().getLinks().entrySet().stream()
                .filter(e -> isSourceLink(e.getKey(), item.getClassificator()))
                .flatMap(e -> e.getValue().getLinkedItems().stream().map(
                        i -> new ClassificatorLinkDaoDto(item.getClassificatorCode(),
                                item.getCode(), e.getKey().getCode(), i.getCode())));
    }

    private boolean isSourceLink(Classificator src, Classificator dst) {
        // filter out all links from a "smaller" classificator to "bigger", comparing by code
        return src.getCode().compareTo(dst.getCode()) > 0;
    }

    public void loadLinks(List<ClassificatorLinkDaoDto> links) {
        if (links != null)
            links.forEach(this::loadLink);

    }

    private void loadLink(ClassificatorLinkDaoDto link) {
        val sourceItem = service.getItem(link.getSrcClsCode(), link.getSrcItemCode());
        val targetItem = service.getItem(link.getDstClsCode(), link.getDstItemCode());
        itemService.linkItem(sourceItem, targetItem);
    }
}
