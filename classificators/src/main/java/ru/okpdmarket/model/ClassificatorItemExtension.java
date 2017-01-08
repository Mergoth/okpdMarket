package ru.okpdmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ClassificatorItemExtension {

    @JsonIgnore
    private final ClassificatorItem item;
    List<ClassificatorItem> children = new ArrayList<>();
    Map<Classificator, ClassificatorLinks> links = new HashMap<>();
    // Calculatable fields
    private int level;
    private List<PathElement> path;


    public ClassificatorItemExtension(ClassificatorItem item) {
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