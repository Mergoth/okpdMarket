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
import java.util.Map;

/**
 * Created by Vladislav on 29.08.2016.
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
        if (extended) {
            clone.setRelations(relations);
            clone.setProperties(properties);
        }
        return clone;
    }

    @Data
    public static class PathElement {
        public final String name;
        public final String code;
    }

}
