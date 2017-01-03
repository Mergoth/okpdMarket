package ru.okpdmarket.repository.impl;

import org.springframework.stereotype.Service;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorContents;
import ru.okpdmarket.repository.ClassificatorRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lalka on 10/9/2016.
 */
@Service
public class ClassificatorRepositoryImpl implements ClassificatorRepository {

    private LinkedHashMap<Classificator, ClassificatorContents> classificatorsMap = new LinkedHashMap<>();


    @Override
    public List<Classificator> getClassificators() {
        return classificatorsMap.keySet().stream().collect(Collectors.toList());
    }

    @Override
    public ClassificatorContents getClassificatorContentsById(String id) {
        //TODO: implement me
        return classificatorsMap.values().iterator().next();
    }

    @Override
    public ClassificatorContents putClassificator(Classificator classificator) {
        ClassificatorContents result = classificatorsMap.putIfAbsent(classificator, new ClassificatorContents(classificator));
        if (result != null) return result;
        return classificatorsMap.get(classificator);
    }
}
