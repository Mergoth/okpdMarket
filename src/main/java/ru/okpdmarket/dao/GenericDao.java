package ru.okpdmarket.dao;

import java.util.List;

public interface GenericDao<E,K> {

    public void remove(E entity);
    public E getById( Object rowKey);
    public List<E> getByNativeQuery(String s);
    public List<E> getAll() ;
    public void save(Object object);
    public void removeById(Object rowKey);

}

