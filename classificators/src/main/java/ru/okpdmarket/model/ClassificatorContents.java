package ru.okpdmarket.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
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
        classificatorItem.setClassificator(classificator);
        elements.putIfAbsent(code, classificatorItem);
        return classificatorItem;
    }

    public ClassificatorItem getItemByCode(String code) {
        return elements.get(code);
    }

    public List<ClassificatorItem> getChildLevel(String code) {
        return elements.get(code).getChildren();
    }

    public List<ClassificatorItem> getFirstLevel() {
        return tree;
    }

    public int size() {
        return elements.size();
    }

}
