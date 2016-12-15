package ru.okpdmarket.dao.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladislav on 11/12/2016.
 */
@Data
public class ClassificatorItemDaoDto {

    @Id
    private String code;
    private String name;
    private String notes;

    private List<ClassificatorItemDaoDto> children = new ArrayList<>();

    //TODO: create converters

}
