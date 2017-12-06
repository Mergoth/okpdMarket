package ru.okpdmarket.dao;

import java.util.List;

public interface BaseClassificatorContentsDao<T> {
    List<T> findByClassificatorCode(String classificatorCode);
}
