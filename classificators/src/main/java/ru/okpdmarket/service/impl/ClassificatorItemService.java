package ru.okpdmarket.service.impl;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.model.ClassificatorLinks;
import ru.okpdmarket.repository.ClassificatorRepository;
import ru.okpdmarket.repository.impl.ClassificatorContents;
import ru.okpdmarket.service.exception.ClassificatorNotFoundException;

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

    public List<ClassificatorItem> addItem(String classificatorId, ClassificatorItem newItem) throws ClassificatorNotFoundException {
        ClassificatorContents contents = repository.getClassificatorByCode(classificatorId).getContents();
        if (contents == null)
            throw new ClassificatorNotFoundException(String.format("Classificator %s not found!", classificatorId));
        ClassificatorItem existingItem = contents.getItemByCode(newItem.getCode());
        if (existingItem != null) {
            newItem = mergeItems(existingItem, newItem);
        }
        contents.putItem(newItem);
        newItem.recalculate();
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
        val targetClassificator = targetItem.getClassificator();
        if (addLink(sourceItem, targetClassificator, targetItem)) {
            addLink(targetItem, sourceItem.getClassificator(), sourceItem);
        }
        sourceItem.recalculate();
        targetItem.recalculate();
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
        return targetLinks.add(linkedItem.clone(false));
    }
}
