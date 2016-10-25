package ru.okpdmarket.model;

import lombok.Data;

import lombok.RequiredArgsConstructor;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Vladislav on 29.08.2016.
 */
@Data
@RequiredArgsConstructor
public class ClassificatorItem implements Serializable {

    private String code;
    private String name;
    private List<ClassificatorItem> children;
    private Classificator classificator;
    private ClassificatorItem parent;

}
