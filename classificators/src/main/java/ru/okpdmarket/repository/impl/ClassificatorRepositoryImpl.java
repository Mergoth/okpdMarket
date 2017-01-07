package ru.okpdmarket.repository.impl;

import org.springframework.stereotype.Service;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.repository.ClassificatorRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by vlad on 10/9/2016.
 */
@Service
public class ClassificatorRepositoryImpl implements ClassificatorRepository {

    private LinkedHashMap<Classificator, ClassificatorContents> classificatorsMap = new LinkedHashMap<>();


    @Override
    public List<Classificator> getClassificators() {
        return new ArrayList<>(classificatorsMap.keySet());
    }

    @Override
    public ClassificatorContents getClassificatorContentsById(String id) {
        Optional<Classificator> classificatorOptional = classificatorsMap.keySet().stream().filter(c -> c.getId().equals(id)).findFirst();
        return classificatorsMap.get(classificatorOptional.orElse(null));
    }

    @Override
    public ClassificatorContents putClassificator(Classificator classificator) {
        ClassificatorContents result = classificatorsMap.putIfAbsent(classificator, new ClassificatorContents(classificator));
        if (result != null) return result;
        return classificatorsMap.get(classificator);
    }
}
