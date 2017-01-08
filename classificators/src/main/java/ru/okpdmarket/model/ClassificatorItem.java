package ru.okpdmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by Vladislav on 29.08.2016.
 */
@Data
@ToString(of = {"code", "name"})
@EqualsAndHashCode(of = {"code"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClassificatorItem {

    @JsonIgnore
    private ClassificatorItemRelations relations = new ClassificatorItemRelations(this);
    @JsonIgnore
    private boolean extended;

    private ClassificatorItemExtension ext = new ClassificatorItemExtension(this);
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
            clone.setRelations(this.relations);
            clone.setExt(this.ext);
        }
        return clone;
    }

}
