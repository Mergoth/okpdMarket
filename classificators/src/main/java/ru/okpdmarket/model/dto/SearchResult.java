package ru.okpdmarket.model.dto;

import lombok.Data;
import ru.okpdmarket.model.ClassificatorItem;

import java.util.List;

@Data
public class SearchResult {
    private final List<ClassificatorItem> resultItems;
    private final Integer hitCount;

}
