package ru.okpdmarket.repository;

import org.springframework.stereotype.Service;
import ru.okpdmarket.model.Classificator;

import java.util.List;

@Service
public interface ClassificatorRepository {

    List<Classificator> getClassificators();

    void updateClassificators(List<Classificator> classificators);

}
