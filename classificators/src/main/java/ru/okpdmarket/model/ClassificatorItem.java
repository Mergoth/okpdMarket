package ru.okpdmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import lombok.RequiredArgsConstructor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladislav on 29.08.2016.
 */
@Data
@RequiredArgsConstructor
public class ClassificatorItem implements Serializable {

    private final String code;
    private final String name;
    @JsonIgnore
    private List<ClassificatorItem> children = new ArrayList<>();
    @JsonIgnore
    private Classificator classificator;
    @JsonIgnore
    private ClassificatorItem parent;

}
