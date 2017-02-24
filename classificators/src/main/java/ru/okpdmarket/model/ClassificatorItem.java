package ru.okpdmarket.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Vladislav on 29.08.2016.
 * Links: {
 * source : {
 * classificatorId  : 1,
 * code         : 1010
 * },
 * targets : {
 * "2" : [
 * {
 * code     : 1100,
 * name     : "Some item name"
 * },
 * {
 * code     : 1101,
 * name     : "Some item name"
 * },
 * {
 * code     : 1102,
 * name     : "Some item name"
 * }
 * ]
 * }
 * }
 */
@Data
@ToString(of = {"code", "name"})
@EqualsAndHashCode(of = {"code"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(value = {"ext", "properties"}, allowGetters = true, ignoreUnknown = true)
public class ClassificatorItem implements Serializable {
    private String code;
    private String name;
    private String notes;
    private String parentCode;

    @JsonIgnore
    private ClassificatorItemRelations relations = new ClassificatorItemRelations(this);

    private Map<String, Object> properties = new HashMap<>();

    public ClassificatorItem() {
        super();
    }

    public ClassificatorItem(String code, String name) {
        this(code, name, "");
    }

    public ClassificatorItem(String code, String name, String notes) {
        this.code = code;
        this.name = name;
        this.notes = notes;
    }

    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return properties;
    }

    @JsonIgnore
    public Object getProp(String name) {
        return properties.get(name);
    }

    @JsonIgnore
    public void setProp(String name, Object value) {
        properties.put(name, value);
    }

    @Override
    public Object clone() {
        ClassificatorItem clone = new ClassificatorItem(this.getCode(), this.getName());
        clone.setNotes(this.getNotes());
        clone.setParentCode(this.getParentCode());
        return clone;
    }

    public ClassificatorItem clone(boolean extended) {
        ClassificatorItem clone = (ClassificatorItem) this.clone();
        clone.setProp("hasChildren", hasChildren());
        if (extended) {
            clone.setRelations(relations);
            clone.setProperties(properties);
        }
        return clone;
    }

    private boolean hasChildren() {
        return !getRelations().getChildren().isEmpty();
    }

    public ClassificatorItem recalculate() {
        setProp("path", calcPath());
        setProp("level", calcLevel());
        setProp("hasChildren", hasChildren());
        setProp("children", calcChildren());
        setProp("links", calcLinks());
        return this;
    }

    private int calcLevel() {
        if (getRelations().getParent() != null) {
            return getRelations().getParent().calcLevel() + 1;
        } else {
            return 0;
        }
    }

    private List<PathElement> calcPath() {
        List<PathElement> res;
        if (!(getParentCode() == null) && !getParentCode().equals("-")) {
            res = new LinkedList<>(
                    (LinkedList<PathElement>) getRelations().getParent().getProperties().get("path")
            );

        } else {
            res = new LinkedList<>();
        }
        res.add(new PathElement(getCode(), getName()));
        return res;
    }

    private List<ClassificatorItem> calcChildren() {
        return getRelations().getChildren().stream().map(i -> i.clone(false)).collect(Collectors.toList());
    }

    private Map<String, ClassificatorLinks> calcLinks() {
        return getRelations().getLinks().entrySet().stream().collect(Collectors.toMap(e -> e.getKey().getCode(), Map.Entry::getValue));
    }

    @Data
    public static class PathElement {
        public final String name;
        public final String code;
    }

}
