package ru.okpdmarket.service;

import org.springframework.stereotype.Service;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.dto.SearchResult;


@Service
public interface SearchService {
    SearchResult searchByClassificator(String classificatorId, String query, Integer take, Integer offset);

    void indexClassificator(Classificator classificator);

}
