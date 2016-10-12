package ru.okpdmarket.repository;

import org.springframework.context.annotation.Bean;
import ru.okpdmarket.model.Classificator;

import java.util.List;

public interface ClassificatorRepository {

    public List<Classificator> getClassificators();
    public List<Classificator> updateClassificators();

}
