package ru.okpdmarket.dao;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.okpdmarket.dao.dto.ClassificatorDaoDto;
import ru.okpdmarket.dao.dto.ClassificatorItemDaoDto;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.service.ClassificatorService;
import ru.okpdmarket.service.impl.ClassificatorItemService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        dto.setId(item.getId());
        dto.setCode(item.getCode());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        val daoDtoChilderList = serializeList(service.getClassificatorFirstLevel(item.getCode()));
        dto.setTree(daoDtoChilderList);
        return dto;
    }

    public List<ClassificatorDaoDto> serializeList(List<Classificator> items) {
        return items.stream().map(this::serialize).collect(Collectors.toList());
    }

    public Classificator deserialize(ClassificatorDaoDto dto) {
        Classificator classificator = new Classificator();
        classificator.setCode(dto.getCode());
        classificator.setName(dto.getName());
        classificator.setId(dto.getId());
        classificator.setDescription(dto.getDescription());
        service.put(classificator);
        loadChildren(null, dto.getTree(), classificator);
        return classificator;
    }

    private void loadChildren(String parentCode, List<ClassificatorItemDaoDto> fromDtoList, Classificator targetClassificator) {
        for (ClassificatorItemDaoDto childDto : fromDtoList) {
            val item = new ClassificatorItem(childDto.getCode(), childDto.getName(), childDto.getNotes());
            item.setParentCode(parentCode);
            itemService.addItem(targetClassificator.getId(), item);

            if (!childDto.getChildren().isEmpty()) {
                loadChildren(childDto.getCode(), childDto.getChildren(), targetClassificator);
            }
        }
    }

    public List<Classificator> deserializeList(List<ClassificatorDaoDto> dtos) {
        return dtos.stream().map(this::deserialize).collect(Collectors.toList());
    }
}
