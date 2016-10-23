package ru.okpdmarket.model;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;


import java.io.Serializable;
import java.util.UUID;

/**
 * Interface for classificator and work with its items
 * Created by Vladislav on 29.08.2016.
 */
@Data
@Table("classificator")
public class Classificator implements Serializable {

    private final String name;

    @PrimaryKey
    private UUID id;
    private String description;
    private LinkedHashMap<String,ClassificatorItem> elements = new LinkedHashMap<>();
    private CopyOnWriteArrayList<ClassificatorItem> tree = new CopyOnWriteArrayList<>();


    public void add(String code, String name) {
        ClassificatorItem classificatorItem = new ClassificatorItem(code,name);
        elements.putIfAbsent(code,classificatorItem);
        tree.addIfAbsent(classificatorItem);
    }

    public ClassificatorItem getItemByCode(String code){

        return elements.get(code);
    }

    public List<ClassificatorItem> getChildLevel(String code){
        return elements.get(code).getChildren();
    }

    public List<ClassificatorItem> getFirstLevel() {
        return tree;
    }

    public int size(){
        return elements.size();
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
