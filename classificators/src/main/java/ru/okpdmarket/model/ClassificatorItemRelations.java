package ru.okpdmarket.model;

import lombok.Data;
import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ClassificatorItemRelations {

    final ClassificatorItem item;

    ClassificatorItem parent;// Relations
    List<ClassificatorItem> children = new ArrayList<ClassificatorItem>();
    Map<Classificator, ClassificatorLinks> links = new HashMap<Classificator, ClassificatorLinks>();
    Classificator classificator;

    public ClassificatorItem linkItem(ClassificatorItem linkedItem) {
        val targetClassificator = linkedItem.getRelations().getClassificator();
        if (addLink(targetClassificator, linkedItem)) {
            linkedItem.getRelations().addLink(this.getClassificator(), item);
        }
        return item;
    }

    private boolean addLink(Classificator targetClassificator, ClassificatorItem linkedItem) {
        val targetLinks = getLinks().computeIfAbsent(targetClassificator, k -> new ClassificatorLinks());
        return targetLinks.add(linkedItem);
    }


    public void setChildren(List<ClassificatorItem> children) {
        this.children = children;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }
}