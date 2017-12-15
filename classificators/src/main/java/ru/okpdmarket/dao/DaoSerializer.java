package ru.okpdmarket.dao;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import ru.okpdmarket.dao.dto.ClassificatorDaoDto;
import ru.okpdmarket.dao.dto.ClassificatorItemDaoDto;
import ru.okpdmarket.dao.dto.ClassificatorLinkDaoDto;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class DaoSerializer {

    public ClassificatorDaoDto serialize(Classificator item) {
        ClassificatorDaoDto dto = new ClassificatorDaoDto();
        dto.setCode(item.getCode());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        return dto;
    }

    private ClassificatorItemDaoDto serialize(ClassificatorItem item) {
        return new ClassificatorItemDaoDto(item);
    }

    public List<ClassificatorItemDaoDto> serializeItems(Classificator classificator) {
        List<ClassificatorItem> childrenItems = Lists.newLinkedList(classificator.getContents().getElements().values());
        return childrenItems.stream().map(this::serialize).collect(Collectors.toList());
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
