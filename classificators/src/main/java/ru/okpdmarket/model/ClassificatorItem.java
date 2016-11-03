package ru.okpdmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladislav on 29.08.2016.
 */
@Data
@RequiredArgsConstructor
@ToString(of = {"code", "name"})
public class ClassificatorItem implements Serializable {

    private final String code;
    private final String name;
    private final String notes;
    private final int level;
    private final String parentCode;
    private List<List<String>> path;
    private List<ClassificatorItem> children = new ArrayList<>();
    @JsonIgnore
    private Classificator classificator;
    @JsonIgnore
    private ClassificatorItem parent;

}
