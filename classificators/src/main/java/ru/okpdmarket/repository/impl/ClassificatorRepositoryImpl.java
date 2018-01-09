package ru.okpdmarket.repository.impl;

import org.springframework.stereotype.Service;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.repository.ClassificatorRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ClassificatorRepositoryImpl implements ClassificatorRepository {

    private LinkedHashMap<String, Classificator> classificatorsMap = new LinkedHashMap<>();

    @Override
    public List<Classificator> getClassificators() {
        return new ArrayList<>(classificatorsMap.values());
    }

    @Override
    public Classificator getClassificatorByCode(String code) {
        return classificatorsMap.get(code);
    }

    @Override
    public Classificator putClassificator(Classificator classificator) {
        if (classificator.getContents() == null)
            classificator.setContents(new ClassificatorContents(classificator));
        Classificator result = classificatorsMap.putIfAbsent(classificator.getCode(), classificator);
        if (result != null) return result;
        return classificator;
    }
}
