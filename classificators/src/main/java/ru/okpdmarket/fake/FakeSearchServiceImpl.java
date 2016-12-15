package ru.okpdmarket.fake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.service.ClassificatorService;
import ru.okpdmarket.service.SearchService;

import java.util.List;

/**
 * Created by Vladislav on 26.10.2016.
 */
@Component
@Profile("debug")
@Primary
public class FakeSearchServiceImpl implements SearchService {

    private final ClassificatorService classificatorService;

    public FakeSearchServiceImpl(@Autowired ClassificatorService classificatorService) {
        this.classificatorService = classificatorService;
    }

    ;

    @Override
    public List<ClassificatorItem> search(String classificatorId, String query) {
        return classificatorService.getClassifiactor(classificatorId).getFirstLevel();
    }

    @Override
    public void indexClassificator(Classificator classificator) {
        //do nothing
    }
}
