package ru.okpdmarket.dao.dto;

import lombok.Data;
import lombok.val;
import org.springframework.data.annotation.Id;
import ru.okpdmarket.model.ClassificatorItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vladislav on 11/12/2016.
 */
@Data
public class ClassificatorItemDaoDto {

    @Id
    private String code;
    private String name;
    private String notes;

    private List<ClassificatorItemDaoDto> children = new ArrayList<>();

    public static class Converter {
        public static ClassificatorItemDaoDto toDto(ClassificatorItem item) {

            ClassificatorItemDaoDto dto = new ClassificatorItemDaoDto();
            dto.setCode(item.getCode());
            dto.setName(item.getName());
            dto.setNotes(item.getNotes());
            val daoDtoChildrenList = ClassificatorItemDaoDto.Converter.toDtoList(item.getChildren());
            dto.setChildren(daoDtoChildrenList);

            return dto;
        }

        public static List<ClassificatorItemDaoDto> toDtoList(List<ClassificatorItem> items) {
            return items.stream().map((item) -> toDto(item)).collect(Collectors.toList());
        }

    }

}
