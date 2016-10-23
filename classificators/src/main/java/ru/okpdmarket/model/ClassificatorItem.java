package ru.okpdmarket.model;

import lombok.Data;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Vladislav on 29.08.2016.
 */
@Table("classificatorItem")
public class ClassificatorItem implements Serializable {

    @PrimaryKey
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private final String code;
    private final String name;
    private List<ClassificatorItem> children;
    private Classificator classificator;
    private ClassificatorItem parent;

}
