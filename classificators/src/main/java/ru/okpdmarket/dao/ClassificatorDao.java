package ru.okpdmarket.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.okpdmarket.dao.dto.ClassificatorDaoDto;

/**
 * Created by lalka on 10/26/2016.
 */
@Repository
public interface ClassificatorDao extends CrudRepository<ClassificatorDaoDto, String> {

}
