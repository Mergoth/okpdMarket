package ru.okpdmarket.model;

import lombok.Data;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.io.Serializable;
import java.util.UUID;

/**
 * Interface for classificator and work with its items
 * Created by Vladislav on 29.08.2016.
 */
@Data
public class Classificator implements Serializable {

    private final String code;
    private final String name;
    private UUID id;
    private String description;
    private LinkedHashMap<String,ClassificatorItem> elements = new LinkedHashMap<>();
    private CopyOnWriteArrayList<ClassificatorItem> tree = new CopyOnWriteArrayList<>();

    public void add(String code, String name) {
        this.add(code,name,null);
    }

    public void add(String code, String name, String parentCode) {
        ClassificatorItem classificatorItem = new ClassificatorItem(code,name);
        classificatorItem.setClassificator(this);
        elements.putIfAbsent(code,classificatorItem);

        if (parentCode!=null) {
            ClassificatorItem parentItem = getItemByCode(parentCode);
            parentItem.getChildren().add(classificatorItem);
            classificatorItem.setParent(parentItem);
        } else {
            tree.addIfAbsent(classificatorItem);
        }
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
}
