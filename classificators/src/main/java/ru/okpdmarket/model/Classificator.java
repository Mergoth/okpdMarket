package ru.okpdmarket.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Interface for classificator and work with its items
 * Created by Vladislav on 29.08.2016.
 */
@Data
public class Classificator {

    // Unique classificator code in english
    private final String code;
    // Classificator name in Russian
    private final String name;
    @Id
    private String id;
    // Classificator description
    private String description;

    @Transient
    private LinkedHashMap<String,ClassificatorItem> elements = new LinkedHashMap<>();

    private CopyOnWriteArrayList<ClassificatorItem> tree = new CopyOnWriteArrayList<>();

    public ClassificatorItem add(String code, String name) {
        ClassificatorItem item = this.add(code, name, null);
        tree.addIfAbsent(item);
        return item;
    }

    public ClassificatorItem add(String code, String name, String parentCode) {

        ClassificatorItem parentItem = getItemByCode(parentCode);
        ClassificatorItem classificatorItem = createClassificatorItem(code, name, parentItem);
        if (parentCode != null && !Objects.equals(parentCode, "")) {
            List<ClassificatorItem> children = parentItem.getChildren();
            children.add(classificatorItem);
            parentItem.setChildren(children);
        } else {
            tree.addIfAbsent(classificatorItem);
        }
        return classificatorItem;
    }

    private ClassificatorItem createClassificatorItem(String code, String name, ClassificatorItem parentItem) {
        ClassificatorItem classificatorItem = new ClassificatorItem(parentItem, code, name);
        classificatorItem.setClassificator(this);
        elements.putIfAbsent(code, classificatorItem);
        return classificatorItem;
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