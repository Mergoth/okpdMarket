package ru.okpdmarket.model.dto;

import lombok.Data;

/**
 * Created by vladislav on 02/01/2017.
 * {
 * source : {
 * classificatorId  : 1,
 * code         : 1010
 * },
 * targets : {
 * "2" : [
 * {
 * code     : 1100,
 * name     : "Some item name"
 * },
 * {
 * code     : 1101,
 * name     : "Some item name"
 * },
 * {
 * code     : 1102,
 * name     : "Some item name"
 * }
 * ]
 * }
 * }
 */
@Data
public class ClassificatorLinkDto {
    private String targetClassificatorId;
    private String targetItemCode;
}
