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
        this(src.getClassificatorCode(),
                src.getCode(),
                dst.getClassificatorCode(),
                dst.getCode()
        );
    }

    public ClassificatorLinkDaoDto(String srcClsCode, String srcItemCode, String dstClsCode, String dstItemCode) {

        this.srcClsCode = srcClsCode;
        this.srcItemCode = srcItemCode;

        this.dstClsCode = dstClsCode;
        this.dstItemCode = dstItemCode;
    }
}
