package ru.okpdmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.okpdmarket.repository.impl.ClassificatorContents;


/**
 * Classificator domain model
 * Created by Vladislav on 29.08.2016.
 */
@Data
public class Classificator {

    private String code;
    private String name;
    private String description;

    @JsonIgnore
    private ClassificatorContents contents;
}