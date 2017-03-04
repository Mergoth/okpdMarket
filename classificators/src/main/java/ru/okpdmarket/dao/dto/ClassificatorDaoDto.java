package ru.okpdmarket.dao.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladislav on 11/12/2016.
 */
@Data
public class ClassificatorDaoDto {

    // Unique classificator code in english
    @Id
    private String code;
    // Classificator name in Russian
    private String name;

    // Classificator description
    private String description;

    private List<ClassificatorItemDaoDto> tree = new ArrayList<>();
    private List<ClassificatorLinkDaoDto> links;

}
