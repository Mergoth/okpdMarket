package ru.okpdmarket.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
    private final ClassificatorItemCached cached = new ClassificatorItemCached(this);
    private final ClassificatorItemRelations relations = new ClassificatorItemRelations(this);
    private String notes;


    public ClassificatorItem(String code, String name) {
        this(code, name, "");
    }

    public ClassificatorItem(String code, String name, String notes) {
        this.code = code;
        this.name = name;
        this.notes = notes;
    }


    public boolean hasChildren() {
        return !relations.getChildren().isEmpty();
    }



}
