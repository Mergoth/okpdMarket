package ru.okpdmarket.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ClassificatorItemRelations {

    final ClassificatorItem item;

    ClassificatorItem parent;// Relations

    Classificator classificator;

    List<ClassificatorItem> children = new ArrayList<>();
    Map<Classificator, ClassificatorLinks> links = new HashMap<>();

  /*  public ClassificatorItem linkItem(ClassificatorItem linkedItem) {
        val targetClassificator = linkedItem.getClassificator();
        if (addLink(targetClassificator, linkedItem)) {
            linkedItem.getRelations().addLink(this.getClassificator(), item);
        }
        return item;
    }

    private boolean addLink(Classificator targetClassificator, ClassificatorItem linkedItem) {
        val targetLinks = getLinks().computeIfAbsent(targetClassificator, k -> new ClassificatorLinks());
        return targetLinks.add(linkedItem);
    }*/


}