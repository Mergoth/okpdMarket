package ru.okpdmarket.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class ClassificatorItemCached {

    private final ClassificatorItem item;

    // Calculatable fields
    private String parentCode;
    private int level;
    private List<PathElement> path;

    public ClassificatorItemCached(ClassificatorItem item) {
        this.item = item;
        this.path = calcPath();
        this.parentCode = calcParentCode();
        this.level = calcLevel();
    }

    private List<PathElement> calcPath() {
        List<PathElement> res;
        if (item.getRelations().getParent() != null) {
            res = new LinkedList<>(item.getRelations().getParent().getCached().getPath());

        } else {
            res = new LinkedList<>();
        }
        res.add(new PathElement(item.getCode(), item.getName()));
        return res;
    }

    private String calcParentCode() {
        if (item.getRelations().getParent() != null) {
            return item.getRelations().getParent().getCode();
        } else {
            return "";
        }
    }


    public int calcLevel() {
        if (item.getRelations().getParent() != null) {
            return item.getRelations().getParent().getCached().calcLevel() + 1;
        } else {
            return 0;
        }
    }

    @Data
    public class PathElement {
        public final String name;
        public final String code;
    }

}