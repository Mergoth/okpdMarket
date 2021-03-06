package ru.okpdmarket.dao.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
public class ClassificatorDaoDto {

    // Unique classificator code in english
    @Id
    private String code;
    // Classificator name in Russian
    private String name;

    // Classificator description
    private String description;
}
