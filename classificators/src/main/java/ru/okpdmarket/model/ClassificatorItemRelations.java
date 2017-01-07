package ru.okpdmarket.model;

import lombok.Data;

@Data
public class ClassificatorItemRelations {

    final ClassificatorItem item;

    ClassificatorItem parent;// Relations

    Classificator classificator;

  /*  public ClassificatorItem linkItem(ClassificatorItem linkedItem) {
        val targetClassificator = linkedItem.getRelations().getClassificator();
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