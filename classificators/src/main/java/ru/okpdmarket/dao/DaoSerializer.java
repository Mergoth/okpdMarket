package ru.okpdmarket.dao;

import org.springframework.stereotype.Service;
import ru.okpdmarket.dao.dto.ClassificatorDaoDto;
import ru.okpdmarket.dao.dto.ClassificatorItemDaoDto;
import ru.okpdmarket.dao.dto.ClassificatorLinkDaoDto;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by vladislav on 09/01/2017.
 */
@Service
public class DaoSerializer {

    public List<ClassificatorDaoDto> serializeList(List<Classificator> items) {
        return items.stream().map(this::serialize).collect(Collectors.toList());
    }

    private ClassificatorDaoDto serialize(Classificator item) {
        ClassificatorDaoDto dto = new ClassificatorDaoDto();
        dto.setCode(item.getCode());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        List<ClassificatorItemDaoDto> daoDtoChilderList = serializeList(item.getContents().getFirstLevel());
        dto.setTree(daoDtoChilderList);
        dto.setLinks(serializeLinks(item));
        return dto;
    }

    private ClassificatorItemDaoDto serialize(ClassificatorItem item) {
        ClassificatorItemDaoDto dto = new ClassificatorItemDaoDto(item);

        // Serialize children
        List<ClassificatorItem> childrenItems = item.getRelations().getChildren();
        List<ClassificatorItemDaoDto> fullChildrenList = childrenItems.stream().map(this::serialize).collect(Collectors.toList());
        dto.setChildren(fullChildrenList);
        return dto;
    }

    public List<ClassificatorItemDaoDto> serializeList(Collection<ClassificatorItem> items) {
        return items.stream().map(this::serialize).collect(Collectors.toList());
    }

    public List<ClassificatorLinkDaoDto> serializeLinks(Classificator classificator) {
        return classificator.getContents().getElements().values().stream()
                .flatMap(this::extractItemLinks)
                .collect(Collectors.toList());
    }

    private Stream<ClassificatorLinkDaoDto> extractItemLinks(ClassificatorItem item) {

        return item.getRelations().getLinks().entrySet().stream()
                .filter(e -> isSourceLink(e.getKey(), item.getClassificator()))
                .flatMap(e -> e.getValue().stream().map(
                        i -> new ClassificatorLinkDaoDto(item.getClassificatorCode(),
                                item.getCode(), e.getKey().getCode(), i.getCode())));
    }

    private boolean isSourceLink(Classificator src, Classificator dst) {
        // filter out all links from a "smaller" classificator to "bigger", comparing by code
        return src.getCode().compareTo(dst.getCode()) > 0;
    }

}
