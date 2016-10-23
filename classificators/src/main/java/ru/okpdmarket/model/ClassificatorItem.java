package ru.okpdmarket.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Vladislav on 29.08.2016.
 */
@Data
public class ClassificatorItem implements Serializable {

    private final String code;
    private final String name;
    private List<ClassificatorItem> children;
    private Classificator classificator;
    private ClassificatorItem parent;

}
