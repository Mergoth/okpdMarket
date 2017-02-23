package ru.okpdmarket.model;

import lombok.Data;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by vladislav on 20/12/2016.
 */
@Data
public class ClassificatorLinks {
    /* private Classificator targetClassificator;
     private ClassificatorItem sourceItem;
 */
    private CopyOnWriteArrayList<ClassificatorItem> linkedItems = new CopyOnWriteArrayList<>();

    public boolean add(ClassificatorItem targetItem) {
        return linkedItems.addIfAbsent(targetItem);
    }


}
