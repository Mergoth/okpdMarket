package ru.okpdmarket.dao.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import ru.okpdmarket.model.ClassificatorItem;

/**
 * Created by vladislav on 11/12/2016.
 */
@Data
public class ClassificatorItemDaoDto {

    @Id
    private String code = "";
    private String name = "";
    private String notes = "";

    private String parentCode;
    private String classificatorCode;

    public ClassificatorItemDaoDto(ClassificatorItem item) {
        code = item.getCode() != null ? item.getCode() : "";
        name = item.getName() != null ? item.getName() : "";
        notes = item.getNotes() != null ? item.getNotes() : "";
        parentCode = item.getParentCode();
        classificatorCode = item.getClassificator().getCode();

    }

    public ClassificatorItemDaoDto() {
        super();
    }

}
