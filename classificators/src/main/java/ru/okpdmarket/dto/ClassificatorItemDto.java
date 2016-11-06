package ru.okpdmarket.dto;

import lombok.Data;
import lombok.ToString;
import ru.okpdmarket.model.ClassificatorItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Vladislav on 04.11.2016.
 */
@Data
@ToString(of = {"code", "name"})
public class ClassificatorItemDto implements Serializable {
    private final String code;
    private final String name;
    private final String notes;
    private final boolean hasChildren;
    private int level;
    private String parentCode;
    private List<ClassificatorItem.PathElement> path;

    private List<ClassificatorItemDto> children = new ArrayList<>();

    public static class Converter {
        public static ClassificatorItemDto toDto(ClassificatorItem item) {
            return toDto(item, false);
        }

        public static ClassificatorItemDto toDto(ClassificatorItem item, boolean extended) {
            String parentCode = Optional.ofNullable(item.getParent()).orElse(new ClassificatorItem(null, "", null)).getCode();
            ClassificatorItemDto dto = new ClassificatorItemDto(item.getCode(), item.getName(), "", item.isHasChildren());
            if (extended) {
                dto.setLevel(item.getLevel());
                dto.setParentCode(item.getParentCode());
                dto.setPath(item.getPath());
                dto.setChildren(item.getChildren().stream()
                        .map(Converter::toDto)
                        .collect(Collectors.toList())
                );
            }
            return dto;
        }

        public static List<ClassificatorItemDto> toDtoList(List<ClassificatorItem> items) {
            return items.stream().map((item) -> toDto(item, true)).collect(Collectors.toList());
        }
    }
}

