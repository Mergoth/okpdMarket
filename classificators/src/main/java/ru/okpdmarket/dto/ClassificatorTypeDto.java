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
    // Unique id
    private final String id;
    // Code name in English for URL
    private final String code;
    // Classificator name in Russian
    private final String name;
    // Classificator description
    private final String description;

    public static class Converter {

        public static ClassificatorTypeDto toDto(Classificator item) {
            // Right now we're using code for ID and code both. Think more about this!
            ClassificatorTypeDto dto = new ClassificatorTypeDto(item.getCode(), item.getCode(), item.getName(), item.getDescription());
            return dto;
        }

        public static List<ClassificatorTypeDto> toDtoList(List<Classificator> items) {
            return items.stream().map((item) -> toDto(item)).collect(Collectors.toList());
        }
    }
}
