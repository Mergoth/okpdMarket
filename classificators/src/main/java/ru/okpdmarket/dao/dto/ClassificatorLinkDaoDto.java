package ru.okpdmarket.dao.dto;

import lombok.Data;
import ru.okpdmarket.model.ClassificatorItem;

/**
 * Created by vladislav on 23/01/2017.
 */
@Data
public class ClassificatorLinkDaoDto {
    private String srcClsCode;
    private String srcItemCode;
    private String dstClsCode;
    private String dstItemCode;

    public ClassificatorLinkDaoDto(ClassificatorItem src, ClassificatorItem dst) {

        srcClsCode = src.getRelations().getClassificator().getCode();
        dstClsCode = dst.getRelations().getClassificator().getCode();

        srcItemCode = src.getCode();
        dstItemCode = dst.getCode();
    }
}
