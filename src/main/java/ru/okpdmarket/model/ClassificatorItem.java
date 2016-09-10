package ru.okpdmarket.model;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by Vladislav on 29.08.2016.
 */
@Data
public class ClassificatorItem {

    private final String code;
    private final String name;
    private ArrayList<ClassificatorItem> children;
    private Classificator classificator;
    private ClassificatorItem parent;

}
