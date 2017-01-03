package ru.okpdmarket.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.okpdmarket.model.ClassificatorItem;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vladislav on 04.11.2016.
 */
@RequiredArgsConstructor
@ToString(of = {"code", "name"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClassificatorItemDto implements Serializable {

    @JsonIgnore
    private final ClassificatorItem item;

    public String getCode() {
        return item.getCode();
    }

    public Boolean getHasChildren() {
        return item.getRelations().hasChildren();
    }


    public static class Converter {
        public static ClassificatorItemDto toDto(ClassificatorItem item) {
            return toDto(item, false);
        }

        public static ClassificatorItemDto toDto(ClassificatorItem item, boolean extended) {
            ClassificatorItemDto dto = new ClassificatorItemDto(item);

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

