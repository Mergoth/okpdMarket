package ru.okpdmarket.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.val;

import java.util.*;

/**
 * Created by Vladislav on 29.08.2016.
 */
@Data
@ToString(of = {"code", "name"})
@EqualsAndHashCode(of = {"code", "name", "notes", "parentCode"})
public class ClassificatorItem {

    // Main fields
    private final String code;
    private final String name;
    private final ClassificatorItem parent;
    // Calculatable fields
    private final String parentCode;
    private final int level;
    private final List<PathElement> path;
    private String notes;
    // Relations
    private List<ClassificatorItem> children = new ArrayList<>();
    private Map<Classificator, ClassificatorLinks> links = new HashMap<>();
    private Classificator classificator;


    public ClassificatorItem(ClassificatorItem parent, String code, String name) {
        this(parent, code, name, "");
    }

    public ClassificatorItem(ClassificatorItem parent, String code, String name, String notes) {
        this.parent = parent;
        this.code = code;
        this.name = name;
        this.notes = notes;
        this.level = calcLevel();
        this.parentCode = calcParentCode();
        this.path = calcPath();
    }

    public ClassificatorItem linkItem(ClassificatorItem linkedItem) {
        val targetClassificator = linkedItem.getClassificator();
        if (addLink(targetClassificator, linkedItem)) {
            linkedItem.addLink(this.getClassificator(), this);
        }
        return this;
    }

    private boolean addLink(Classificator targetClassificator, ClassificatorItem linkedItem) {
        val targetLinks = links.computeIfAbsent(targetClassificator, k -> new ClassificatorLinks());
        return targetLinks.add(linkedItem);
    }


    private List<PathElement> calcPath() {
        List<PathElement> res;
        if (this.parent != null) {
            res = new LinkedList<>(parent.getPath());

        } else {
            res = new LinkedList<>();
        }
        res.add(new PathElement(this.getCode(), this.getName()));
        return res;
    }

    private String calcParentCode() {
        if (this.parent != null) {
            return this.parent.getCode();
        } else {
            return "";
        }
    }


    private int calcLevel() {
        if (this.parent != null) {
            return this.parent.calcLevel() + 1;
        } else {
            return 0;
        }
    }


    public void setChildren(List<ClassificatorItem> children) {
        this.children = children;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }


    @Data
    public class PathElement {
        public final String name;
        public final String code;


    }
}
