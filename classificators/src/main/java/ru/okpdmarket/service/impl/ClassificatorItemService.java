package ru.okpdmarket.service.impl;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.model.ClassificatorItemCached;
import ru.okpdmarket.model.ClassificatorLinks;
import ru.okpdmarket.repository.ClassificatorRepository;
import ru.okpdmarket.repository.impl.ClassificatorContents;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
        item.getCached().setPath(calcPath(item));
        item.getCached().setLevel(calcLevel(item));
        return item;
    }

    private List<ClassificatorItemCached.PathElement> calcPath(ClassificatorItem item) {
        List<ClassificatorItemCached.PathElement> res;
        if (item.getRelations().getParent() != null) {
            res = new LinkedList<>(item.getRelations().getParent().getCached().getPath());

        } else {
            res = new LinkedList<>();
        }
        res.add(new ClassificatorItemCached.PathElement(item.getCode(), item.getName()));
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
        return newItem.getRelations().getParent().getCached().getChildren();
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

    private boolean addLink(ClassificatorItem sourceItem, Classificator targetClassificator, ClassificatorItem linkedItem) {
        val targetLinks = sourceItem.getCached().getLinks().computeIfAbsent(targetClassificator, k -> new ClassificatorLinks());
        return targetLinks.add(linkedItem);
    }
}
