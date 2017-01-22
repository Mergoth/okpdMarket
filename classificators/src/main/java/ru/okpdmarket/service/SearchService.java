package ru.okpdmarket.service;

import org.springframework.stereotype.Service;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;

import java.util.List;

/**
 * Created by Vladislav on 26.10.2016.
 */
@Service
public interface SearchService {
    List<ClassificatorItem> searchByClassificator(String classificatorId, String query);

    void indexClassificator(Classificator classificator);


}
