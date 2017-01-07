package ru.okpdmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by Vladislav on 29.08.2016.
 */
@Data
@ToString(of = {"code", "name"})
@EqualsAndHashCode(of = {"code"})
public class ClassificatorItem {

    @JsonIgnore
    private final ClassificatorItemRelations relations = new ClassificatorItemRelations(this);
    @JsonIgnore
    private final ClassificatorItemCached cached = new ClassificatorItemCached(this);
    // Main fields
    private String code;
    private String name;
    private String notes;
    private String parentCode;


    public ClassificatorItem(String code, String name) {
        this(code, name, "");
    }

    public ClassificatorItem(String code, String name, String notes) {
        this.code = code;
        this.name = name;
        this.notes = notes;
    }

}
