package ru.okpdmarket.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.okpdmarket.dao.dto.ClassificatorItemDaoDto;

@Repository(value = "classificatorItems")
public interface ClassificatorItemsDao extends CrudRepository<ClassificatorItemDaoDto, String>, BaseClassificatorContentsDao<ClassificatorItemDaoDto> {

}
