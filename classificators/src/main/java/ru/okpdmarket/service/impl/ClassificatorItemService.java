package ru.okpdmarket.service.impl;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.model.ClassificatorLinks;
import ru.okpdmarket.repository.ClassificatorRepository;
import ru.okpdmarket.repository.impl.ClassificatorContents;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by vladislav on 06/01/2017.
 */
@Service
public class ClassificatorItemService {

    ClassificatorRepository repository;

    @Autowired
    public ClassificatorItemService(ClassificatorRepository classificatorRepository) {
        this.repository = classificatorRepository;
    }

    public ClassificatorItem recalculate(ClassificatorItem item) {
        item.setProp("path", calcPath(item));
        item.setProp("level", calcLevel(item));
        item.setProp("hasChildren", !item.getRelations().getChildren().isEmpty());
        item.setProp("children", calcChildren(item));
        item.setProp("links", calcLinks(item));
        return item;
    }

    private Map<Classificator, ClassificatorLinks> calcLinks(ClassificatorItem item) {
        // FIXME: figure out correct implementation
        return null;
    }

    private List<ClassificatorItem> calcChildren(ClassificatorItem item) {
        return item.getRelations().getChildren().stream().map(i -> i.clone(false)).collect(Collectors.toList());
    }

    private List<ClassificatorItem.PathElement> calcPath(ClassificatorItem item) {
        List<ClassificatorItem.PathElement> res;
        if (!item.getParentCode().equals("-")) {
            res = new LinkedList<>(
                    (LinkedList<ClassificatorItem.PathElement>) item.getRelations().getParent().getProperties().get("path")
            );

        } else {
            res = new LinkedList<>();
        }
        res.add(new ClassificatorItem.PathElement(item.getCode(), item.getName()));
        return res;
    }

    public int calcLevel(ClassificatorItem item) {
        if (item.getRelations().getParent() != null) {
            return calcLevel(item.getRelations().getParent()) + 1;
        } else {
            return 0;
        }
    }

    public List<ClassificatorItem> addItem(String classificatorId, ClassificatorItem newItem) {
        ClassificatorContents contents = repository.getClassificatorContentsById(classificatorId);
        ClassificatorItem existingItem = contents.getItemByCode(newItem.getCode());
        if (existingItem != null) {
            newItem = mergeItems(existingItem, newItem);
        }
        contents.putItem(newItem);
        recalculate(newItem);
        return newItem.getRelations().getParent().getRelations().getChildren();
    }

    private ClassificatorItem mergeItems(ClassificatorItem currentItem, ClassificatorItem newItem) {
        if (!Objects.equals(currentItem.getParentCode(), newItem.getParentCode())) {
            currentItem.setParentCode(newItem.getParentCode());
        }
        currentItem.setName(newItem.getName());
        currentItem.setNotes(newItem.getNotes());
        return currentItem;
    }

    public ClassificatorItem linkItem(ClassificatorItem sourceItem, ClassificatorItem targetItem) {
        val targetClassificator = targetItem.getRelations().getClassificator();
        if (addLink(sourceItem, targetClassificator, targetItem)) {
            addLink(targetItem, sourceItem.getRelations().getClassificator(), sourceItem);
        }
        return sourceItem;
    }

    /**
     * Add link to in-memory model of Classificator Item. Gets or creates ClassificatorLinks aggregate in map for target classificator.
     * Then tries to add new link to the list.
     *
     * @param sourceItem          - source item, where link goes from. Source and target can be reversed since link always should be two-way
     * @param targetClassificator - Classificator of target
     * @param linkedItem          - target Item to link
     * @return 'true' if added new element, 'false' if this element was already on the list.
     */
    private boolean addLink(ClassificatorItem sourceItem, Classificator targetClassificator, ClassificatorItem linkedItem) {
        val targetLinks = sourceItem.getRelations().getLinks().computeIfAbsent(targetClassificator, k -> new ClassificatorLinks());
        return targetLinks.add(linkedItem);
    }
}
