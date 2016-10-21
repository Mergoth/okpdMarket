package ru.okpdmarket.repository;

import ru.okpdmarket.model.Classificator;

import java.util.List;

public interface ClassificatorRepository {

    List<Classificator> getClassificators();

    List<Classificator> updateClassificators();

}