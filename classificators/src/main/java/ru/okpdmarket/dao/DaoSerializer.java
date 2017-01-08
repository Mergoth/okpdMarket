package ru.okpdmarket.dao;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.okpdmarket.dao.dto.ClassificatorDaoDto;
import ru.okpdmarket.dao.dto.ClassificatorItemDaoDto;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.service.ClassificatorService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vladislav on 09/01/2017.
 */
@Service
public class DaoSerializer {

    private final ClassificatorService service;

    @Autowired
    public DaoSerializer(ClassificatorService service) {
        this.service = service;
    }

    private ClassificatorItemDaoDto serialize(ClassificatorItem item) {
        String classificatorId = item.getRelations().getClassificator().getId();
        ClassificatorItemDaoDto dto = new ClassificatorItemDaoDto();
        dto.setCode(item.getCode());
        dto.setName(item.getName());
        dto.setNotes(item.getNotes());

        // Serialize children
        val childrenStubs = item.getExt().getChildren();
        val fullChildrenList = childrenStubs.stream().map(stub -> {
            ClassificatorItem expanded = service.getItem(classificatorId, stub.getCode());
            expanded.getExt().setChildren(expanded.getExt().getChildren());
            return serialize(expanded);
        }).collect(Collectors.toList());
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
/*
    public static Classificator fromDto(ClassificatorDaoDto dto) {
        Classificator classificator = new Classificator();
        classificator.setCode(dto.getCode());
        classificator.setName(dto.getName());
        classificator.setId(dto.getId());
        classificator.setDescription(dto.getDescription());
        loadChildren(null, dto.getTree(), classificator);
        return classificator;
    }

    private static void loadChildren(String parentCode, List<ClassificatorItemDaoDto> fromDtoList, Classificator targetClassificator) {
        for (ClassificatorItemDaoDto childDto : fromDtoList) {
            //ClassificatorItem childItem = targetClassificator.add(childDto.getCode(), childDto.getName(), parentCode);
            // childItem.setNotes(childDto.getNotes());
            if (!childDto.getChildren().isEmpty()) {
                loadChildren(childDto.getCode(), childDto.getChildren(), targetClassificator);
            }
        }
    }

    public static List<Classificator> fromDtoList(List<ClassificatorDaoDto> dtos) {
        return dtos.stream().map(Converter::fromDto).collect(Collectors.toList());
    }*/
}
