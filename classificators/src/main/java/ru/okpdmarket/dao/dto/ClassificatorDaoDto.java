package ru.okpdmarket.dao.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import ru.okpdmarket.model.Classificator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vladislav on 11/12/2016.
 */
@Data
public class ClassificatorDaoDto {

    @Id
    private String id;

    // Unique classificator code in english
    private String code;
    // Classificator name in Russian
    private String name;

    // Classificator description
    private String description;

    private List<ClassificatorItemDaoDto> tree = new ArrayList<>();

    public static class Converter {

        public static ClassificatorDaoDto toDto(Classificator item) {
            ClassificatorDaoDto dto = new ClassificatorDaoDto();
            dto.setId(item.getId());
            dto.setCode(item.getCode());
            dto.setName(item.getName());
            dto.setDescription(item.getDescription());
            // val daoDtoChilderList = ClassificatorItemDaoDto.Converter.toDtoList(item.);
            //dto.setTree(daoDtoChilderList);
            return dto;
        }

        public static List<ClassificatorDaoDto> toDtoList(List<Classificator> items) {
            return items.stream().map(Converter::toDto).collect(Collectors.toList());
        }

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
        }
    }
}
