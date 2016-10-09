package ru.okpdmarket.dao.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.impetus.client.cassandra.common.CassandraConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import ru.okpdmarket.dao.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@Repository
public class GenericDaoImpl<T,K> implements GenericDao<T,K> {

    protected final Log log = LogFactory.getLog(getClass());
    protected Class<T> persistentClass;
    public EntityManager entityManager=null;
    EntityManagerFactory entityManagerFactory=null;

    public GenericKunderaDaoImpl(final Class<T> persistentClass, String persistanceUnit) {
        this.persistentClass = persistentClass;
        createEntityManager(persistanceUnit);
    }

    @Override
    public List<T> getAll() {
        // Try * instead of p
        String query="Select p from "+persistentClass.getName()+" p";
        Query q=entityManager.createQuery(query);
        return q.getResultList();
    }

    @Override
    public T getById( Object rowKey) {
        return (T) entityManager.find(this.persistentClass, rowKey);
    }

    @Override
    public List<T> getByNativeQuery(String s) {
        Query customQuery = entityManager.createNativeQuery(s,persistentClass);
        return customQuery.getResultList();
    }

    @Override
    public void save(Object object) {
        entityManager.persist(object);
    }

    @Override
    public void removeById(Object rowKey) {
        Object object=getById(rowKey);
        remove(object);
    }

    @Override
    public void remove(Object object) {
        entityManager.remove(object);
    }

    private void createEntityManager(String persistanceUnit) {
        Map<String, String> propertyMap = new HashMap<String, String>();
        propertyMap.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
        entityManagerFactory = Persistence.createEntityManagerFactory(persistanceUnit, propertyMap);
        entityManager = entityManagerFactory.createEntityManager();
    }

    public static Integer generateHashKey(String[] keys) {
        Integer hashKey=1;
        for (String key : keys){
            hashKey*=key.hashCode();
        }
        return hashKey*PRIME_NR/PRIME_DR;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (entityManager != null) {
            entityManager.close();
            entityManager=null;
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
            entityManagerFactory=null;
        }
    }
}