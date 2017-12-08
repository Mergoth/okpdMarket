package ru.okpdmarket.dao.dto;

import lombok.Data;


@Data
public class ClassificatorLinkDaoDto {
    private String classificatorCode;
    private String itemCode;
    private String destinationClassificatorCode;
    private String destinationItemCode;

    public ClassificatorLinkDaoDto() {
        super();
    }

    public ClassificatorLinkDaoDto(String classificatorCode, String itemCode, String destinationClassificatorCode, String destinationItemCode) {

        this.classificatorCode = classificatorCode;
        this.itemCode = itemCode;

        this.destinationClassificatorCode = destinationClassificatorCode;
        this.destinationItemCode = destinationItemCode;
    }
}
