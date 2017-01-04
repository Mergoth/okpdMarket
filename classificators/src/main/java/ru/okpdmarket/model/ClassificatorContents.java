package ru.okpdmarket.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by vladislav on 03/01/2017.
 */
@Getter
@RequiredArgsConstructor
public class ClassificatorContents {

    private final Classificator classificator;

    private LinkedHashMap<String, ClassificatorItem> elements = new LinkedHashMap<>();

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
            Set<ClassificatorItem> children = parentItem.getRelations().getChildren();
            children.add(classificatorItem);
            //parentItem.getRelations().setChildren(children);
        } else {
            tree.addIfAbsent(classificatorItem);
        }
        return classificatorItem;
    }

    private ClassificatorItem createClassificatorItem(String code, String name, ClassificatorItem parentItem) {
        ClassificatorItem classificatorItem = new ClassificatorItem(code, name);
        classificatorItem.getRelations().setParent(parentItem);
        classificatorItem.getRelations().setClassificator(classificator);
        classificatorItem.getCached().recalculate();
        elements.putIfAbsent(code, classificatorItem);
        return classificatorItem;
    }

    public ClassificatorItem getItemByCode(String code) {
        return elements.get(code);
    }

    public Set<ClassificatorItem> getChildLevel(String code) {
        return elements.get(code).getRelations().getChildren();
    }

    public List<ClassificatorItem> getFirstLevel() {
        return tree;
    }

    public int size() {
        return elements.size();
    }

}
