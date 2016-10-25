package ru.okpdmarket.service;

import org.springframework.stereotype.Service;
import ru.okpdmarket.model.ClassificatorItem;

import java.util.List;

/**
 * Created by Vladislav on 26.10.2016.
 */
@Service
public interface SearchService {
    List<ClassificatorItem> search(int classificatorId, String query);
}
