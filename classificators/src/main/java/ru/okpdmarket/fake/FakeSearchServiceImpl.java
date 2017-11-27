package ru.okpdmarket.fake;

import lombok.RequiredArgsConstructor;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.service.ClassificatorService;
import ru.okpdmarket.service.SearchService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Vladislav on 26.10.2016.
 */
/*
@Component
@Profile("debug")
@Primary*/
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class FakeSearchServiceImpl implements SearchService {

    private final ClassificatorService classificatorService;

    @Override
    public List<ClassificatorItem> searchByClassificator(String classificatorCode, String query) {
        return classificatorService.getClassificatorFirstLevel(classificatorCode);
    }

    @Override
    public void indexClassificator(Classificator classificator) {
        //do nothing
    }
}
