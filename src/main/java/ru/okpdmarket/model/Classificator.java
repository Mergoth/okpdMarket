package ru.okpdmarket.model;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Interface for classificator and work with its items
 * Created by Vladislav on 29.08.2016.
 */
@Data
public class Classificator {

    private final String name;
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
        return null;
    }

    public List<ClassificatorItem> getFirstLevel() {

        return tree;
    }

    public int size(){

        return elements.size();
    }


}
