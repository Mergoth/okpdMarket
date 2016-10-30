package ru.okpdmarket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.service.ClassificatorService;
import ru.okpdmarket.service.SearchService;

import java.util.List;

/**
 * Created by Vladislav on 26.10.2016.
 */
@Component
public class FakeSearchServiceImpl implements SearchService {
    @Autowired
    ClassificatorService classificatorService;

    @Override
    public List<ClassificatorItem> search(String classificatorId, String query) {
        return classificatorService.getClassifiactor(classificatorId).getFirstLevel();
    }
}
