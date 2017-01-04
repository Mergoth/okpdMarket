package ru.okpdmarket.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.RequiredArgsConstructor;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.model.ClassificatorItemCached;
import ru.okpdmarket.model.ClassificatorLinks;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Vladislav on 04.11.2016.
 * {
 "code" : "1",
 "name" : "Test",
 "hasChildren" : true,
 "level" : 0,
 "path" : [ {
 "name" : "1",
 "code" : "Test"
 } ],
 "links" : {
 "2" : [ {
 "code" : "1",
 "name" : "TestTnvd",
 "hasChildren" : false
 } ]
 },
 "children" : [ {
 "code" : "11",
 "name" : "TestLevel11",
 "hasChildren" : false
 }, {
 "code" : "12",
 "name" : "TestLevel12",
 "hasChildren" : true
 }, {
 "code" : "13",
 "name" : "TestLevel13",
 "hasChildren" : false
 } ]
 }
 */
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClassificatorItemDto implements Serializable {

    @JsonIgnore
    private final ClassificatorItem item;

    private final boolean extended;

    public String getCode() {
        return item.getCode();
    }

    public String getName() {
        return item.getName();
    }

    public Boolean getHasChildren() {
        return item.getRelations().hasChildren();
    }

    //level
    public Integer getLevel() {
        if (!extended) {
            return null;
        }
        return item.getCached().getLevel();
    }

    //path
    public List<ClassificatorItemCached.PathElement> getPath() {
        if (!extended) {
            return null;
        }
        return item.getCached().getPath();
    }

    //links
    public Map<Classificator, ClassificatorLinks> getLinks() {
        if (!extended) {
            return null;
        }
        return item.getRelations().getLinks();
    }

    //children
    public List<ClassificatorItemDto> getChildren() {
        if (!extended) {
            return null;
        }
        return item.getRelations().getChildren().stream().map(child -> new ClassificatorItemDto(child, false)).collect(Collectors.toList());
    }

    public static class Converter {
        public static ClassificatorItemDto toDto(ClassificatorItem item) {
            return toDto(item, false);
        }

        public static ClassificatorItemDto toDto(ClassificatorItem item, boolean extended) {
            ClassificatorItemDto dto = new ClassificatorItemDto(item, extended);

            /*ClassificatorItemDto dto = new ClassificatorItemDto(item.getCode(), item.getName(), "", item.hasChildren());
            if (extended) {
                dto.setLevel(item.getLevel());
                dto.setParentCode(item.getParentCode());
                dto.setPath(item.getPath());
                dto.setChildren(item.getChildren().stream()
                        .map(Converter::toDto)
                        .collect(Collectors.toList()));
                Map<String, List<ClassificatorItemDto>> linksDto =
                        item.getLinks().entrySet().stream().collect(
                                Collectors.toMap(
                                        (c) -> c.getKey().getId(),
                                        (i) -> Converter.toDtoList(i.getValue().getLinkedItems(), false)
                                ));
                dto.setLinks(linksDto);
            }*/
            return dto;
        }

        public static List<ClassificatorItemDto> toDtoList(List<ClassificatorItem> items, boolean extended) {
            return items.stream().map((item) -> toDto(item, extended)).collect(Collectors.toList());
        }
    }
}

