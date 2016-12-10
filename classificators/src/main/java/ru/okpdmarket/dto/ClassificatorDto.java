package ru.okpdmarket.dto;


import ru.okpdmarket.model.Classificator;

/**
 * Created by lalka on 11/17/2016.
 */
// TODO: do we need this?
@Deprecated
public class ClassificatorDto {

    private  String code;
    // Classificator name in Russian
    private  String name;

    private String id;
    // Classificator description
    private String description;

    ClassificatorDto(Classificator classificator){
        this.code = classificator.getCode();
        this.name = classificator.getName();
        this.id = classificator.getId();
        this.description = classificator.getDescription();
    }
}
