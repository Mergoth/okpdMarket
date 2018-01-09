package ru.okpdmarket.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.okpdmarket.dao.dto.ClassificatorLinkDaoDto;

/**
 * Created by lalka on 10/26/2016.
 */
@Repository(value = "classificatorLinks")
public interface ClassificatorLinksDao extends CrudRepository<ClassificatorLinkDaoDto, String>, BaseClassificatorContentsDao<ClassificatorLinkDaoDto> {
}
