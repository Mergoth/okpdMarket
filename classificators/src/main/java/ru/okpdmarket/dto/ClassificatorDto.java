package ru.okpdmarket.dto;


import ru.okpdmarket.model.Classificator;

import java.util.UUID;

/**
 * Created by lalka on 11/17/2016.
 */
public class ClassificatorDto {

    private  String code;
    // Classificator name in Russian
    private  String name;

    private UUID id;
    // Classificator description
    private String description;

    ClassificatorDto(Classificator classificator){
        this.code = classificator.getCode();
        this.name = classificator.getName();
        this.id = classificator.getId();
        this.description = classificator.getDescription();
    }
}
