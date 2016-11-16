package ru.okpdmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vladislav on 29.08.2016.
 */
@Data
@ToString(of = {"code", "name"})
@Document
public class ClassificatorItem implements Serializable {

    @Id
    private final String code;
    private final String name;
    private final String notes;

    @JsonIgnore
    private final ClassificatorItem parent;
    private final String parentCode;
    private final int level;
    private final List<PathElement> path;

    @JsonIgnore
    private List<ClassificatorItem> children = new ArrayList<>();
    private boolean hasChildren = false;

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
        this.path = calcPath();
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
        this.hasChildren = !children.isEmpty();
    }

    @Data
    public class PathElement {
        public final String name;
        public final String code;


    }
}
