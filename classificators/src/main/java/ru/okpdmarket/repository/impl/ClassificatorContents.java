package ru.okpdmarket.repository.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by vladislav on 03/01/2017.
 */
@Getter
@RequiredArgsConstructor
public class ClassificatorContents {

    private final static String TOP_CODE = "-";

    private final Classificator classificator;

    private LinkedHashMap<String, ClassificatorItem> elements = new LinkedHashMap<>();


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
        item.getRelations().setClassificator(classificator);
        parentItem.getRelations().getChildren().add(item);
        //parentItem.recalculate();
        return parentItem.getRelations().getChildren();

    }

    private ClassificatorItem createOrGet(String itemCode) {
        if (elements.containsKey(itemCode))
            return elements.get(itemCode);

        ClassificatorItem tempStub = new ClassificatorItem(itemCode, "tempStub");
        tempStub.getRelations().setClassificator(classificator);
        tempStub.recalculate();
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
        if (elements.size() > 0) {
            if (elements.get(TOP_CODE) == null)
                throw new RuntimeException("Incorrect classificator contents state. No top coded item!");
            return elements.get(TOP_CODE).getRelations().getChildren();
        }
        return Collections.emptyList();
    }

    public void traverseItems(Consumer<ClassificatorItem> itemConsumer) {
        elements.entrySet().stream().filter(e -> !Objects.equals(e.getKey(), TOP_CODE)).forEach((e) -> itemConsumer.accept(e.getValue()));
    }

    public int size() {
        return elements.size();
    }

}
