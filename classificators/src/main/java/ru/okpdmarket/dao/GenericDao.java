package ru.okpdmarket.dao;

import java.util.List;

public interface GenericDao<E,K> {

    void remove(E entity);

    E getById(Object rowKey);

    List<E> getByNativeQuery(String s);

    List<E> getAll();

    void save(Object object);

    void removeById(Object rowKey);

}

