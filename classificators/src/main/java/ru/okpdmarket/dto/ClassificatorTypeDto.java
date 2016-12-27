package ru.okpdmarket.dto;

import lombok.Data;
import ru.okpdmarket.model.Classificator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vladislav on 15.10.2016.
 */
@Data
public class ClassificatorTypeDto {
    private final String name;
    private final String id;

    public static class Converter {

        public static ClassificatorTypeDto toDto(Classificator item) {
            ClassificatorTypeDto dto = new ClassificatorTypeDto(item.getName(), item.getCode());
            return dto;
        }

        public static List<ClassificatorTypeDto> toDtoList(List<Classificator> items) {
            return items.stream().map((item) -> toDto(item)).collect(Collectors.toList());
        }
    }
}
