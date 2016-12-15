package ru.okpdmarket.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.okpdmarket.annotation.CascadeSave;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Interface for classificator and work with its items
 * Created by Vladislav on 29.08.2016.
 */
@Data
@Document
public class Classificator implements Serializable {

    // Unique classificator code in english
    private final String code;
    // Classificator name in Russian
    private final String name;
    @Id
    private String id;
    // Classificator description
    private String description;

    @CascadeSave
    @DBRef
    private LinkedHashMap<String,ClassificatorItem> elements = new LinkedHashMap<>();
    @DBRef
    private CopyOnWriteArrayList<ClassificatorItem> tree = new CopyOnWriteArrayList<>();

    public void add(String code, String name) {

        tree.addIfAbsent(this.add(code, name, null));
    }

    public ClassificatorItem add(String code, String name, String parentCode) {

        // FIXME: clean this mess

        ClassificatorItem parentItem = getItemByCode(parentCode);
        ClassificatorItem classificatorItem = createClassificatorItem(code, name, parentItem);
        if (parentCode!=null) {
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