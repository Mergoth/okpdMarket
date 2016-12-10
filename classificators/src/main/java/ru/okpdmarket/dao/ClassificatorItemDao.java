package ru.okpdmarket.dao;

import org.springframework.data.repository.CrudRepository;
import ru.okpdmarket.model.ClassificatorItem;

/**
 * Created by frostymaster on 04.12.2016.
 */
public interface ClassificatorItemDao extends CrudRepository<ClassificatorItem, String> {
}
