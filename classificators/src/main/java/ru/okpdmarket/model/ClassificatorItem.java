package ru.okpdmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vladislav on 29.08.2016.
 */
@Data
@ToString(of = {"code", "name"})
public class ClassificatorItem implements Serializable {

    private final String code;
    private final String name;
    private final String notes;
    private final int level;
    private final String parentCode;
    @JsonIgnore
    private final ClassificatorItem parent;
    private List<PathElement> path;
    @JsonIgnore
    private List<ClassificatorItem> children = new ArrayList<>();
    @JsonIgnore
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
        calcPath();
    }

    private void calcPath() {
        if (this.parent != null) {
            this.path = new LinkedList<>(parent.getPath());

        } else {
            this.path = new LinkedList<>();
        }
        this.path.add(new PathElement(this.getCode(), this.getName()));
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

    @Data
    private class PathElement {
        public final String name;
        public final String code;


    }
}
