package ru.okpdmarket.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ClassificatorItemCached {

    private final ClassificatorItem item;
    List<ClassificatorItem> children = new ArrayList<>();
    Map<Classificator, ClassificatorLinks> links = new HashMap<>();
    // Calculatable fields
    private int level;
    private List<PathElement> path;


    public ClassificatorItemCached(ClassificatorItem item) {
        this.item = item;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    @Data
    public static class PathElement {
        public final String name;
        public final String code;
    }

}