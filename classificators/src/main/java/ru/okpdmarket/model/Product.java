package ru.okpdmarket.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import ru.okpdmarket.model.helper.CompanyLocation;
import ru.okpdmarket.model.helper.OKVED;
import ru.okpdmarket.model.helper.PhoneNumber;

import java.util.List;

/**
 * Created by Vladislav on 15.08.2016.
 */
@Data
public class Product {
    private Integer id;
    private String name;
    private List<PhoneNumber> phones;
    private List<Email> emails;
    private List<CompanyLocation> locations;
    private List<OKVED> okveds;



}
