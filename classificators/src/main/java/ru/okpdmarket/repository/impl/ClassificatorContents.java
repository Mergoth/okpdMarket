package ru.okpdmarket.repository.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by vladislav on 03/01/2017.
 */
@Getter
@RequiredArgsConstructor
public class ClassificatorContents {

    private final static String TOP_CODE = "-";

    private final Classificator classificator;

    private LinkedHashMap<String, ClassificatorItem> elements = new LinkedHashMap<>();

   /* public ClassificatorItem add(String code, String name) {
        ClassificatorItem item = this.add(code, name, null);
        tree.addIfAbsent(item);
        return item;
    }

    public ClassificatorItem add(String code, String name, String parentCode) {

        ClassificatorItem parentItem = getItemByCode(parentCode);
        ClassificatorItem classificatorItem = createClassificatorItem(code, name, parentItem);
        if (parentCode != null && !Objects.equals(parentCode, "")) {
            Set<ClassificatorItem> children = parentItem.getExt().getChildren();
            children.add(classificatorItem);
            //parentItem.getRelations().setChildren(children);
        } else {
            tree.addIfAbsent(classificatorItem);
        }
        return classificatorItem;
    }*/

    public ClassificatorItem putItem(ClassificatorItem newItem) {
        positionItem(newItem);
        return elements.put(newItem.getCode(), newItem);
    }

    private List<ClassificatorItem> positionItem(ClassificatorItem item) {
        String parentCode = item.getParentCode();
        if (parentCode == null || Objects.equals(parentCode, "")) {
            item.setParentCode(TOP_CODE);
        }
        ClassificatorItem parentItem = createOrGet(item.getParentCode());
        item.getRelations().setParent(parentItem);

        parentItem.getRelations().getChildren().add(item);
        return parentItem.getRelations().getChildren();

    }

    private ClassificatorItem createOrGet(String itemCode) {
        if (elements.containsKey(itemCode))
            return elements.get(itemCode);

        ClassificatorItem tempStub = new ClassificatorItem(itemCode, "tempStub");
        tempStub.getRelations().setClassificator(classificator);
        elements.put(itemCode, tempStub);
        return tempStub;
    }

    public ClassificatorItem getItemByCode(String code) {
        return elements.get(code);
    }

    public List<ClassificatorItem> getChildLevel(String code) {
        return elements.get(code).getRelations().getChildren();
    }

    public List<ClassificatorItem> getFirstLevel() {
        return elements.get(TOP_CODE).getRelations().getChildren();
    }

    public int size() {
        return elements.size();
    }

}