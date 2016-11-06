package ru.okpdmarket.dto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.okpdmarket.model.ClassificatorItem;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Vladislav on 06.11.2016.
 */
public class ClassificatorItemDtoConverterTest {


    @Test
    public void toDtoExtended() throws Exception {
        ClassificatorItem parentItem = new ClassificatorItem(null, "1", "parentName");
        ClassificatorItem item1 = new ClassificatorItem(parentItem, "1.1", "item1Name");
        ClassificatorItem item2 = new ClassificatorItem(parentItem, "1.2", "item2Name");
        ClassificatorItem item21 = new ClassificatorItem(parentItem, "1.2.1", "item21Name");
        item2.setChildren(Collections.singletonList(item21));
        parentItem.setChildren(Arrays.asList(item1, item2));

        ClassificatorItemDto dto = ClassificatorItemDto.Converter.toDto(parentItem, true);
        Assert.assertEquals("1", dto.getCode());
        Assert.assertEquals("parentName", dto.getName());
        Assert.assertEquals(Integer.valueOf(0), dto.getLevel());
        Assert.assertEquals("item1Name", dto.getChildren().get(0).getName());
        Assert.assertEquals(2, dto.getChildren().size());
        Assert.assertEquals(0, dto.getChildren().get(1).getChildren().size());
    }

    @Test
    public void toDtoSimple() throws Exception {
        ClassificatorItem parentItem = new ClassificatorItem(null, "1", "parentName");
        ClassificatorItem item1 = new ClassificatorItem(parentItem, "1.1", "item1Name");
        ClassificatorItem item2 = new ClassificatorItem(parentItem, "1.2", "item2Name");
        ClassificatorItem item21 = new ClassificatorItem(parentItem, "1.2.1", "item21Name");
        item2.setChildren(Collections.singletonList(item21));
        parentItem.setChildren(Arrays.asList(item1, item2));

        ClassificatorItemDto dto = ClassificatorItemDto.Converter.toDto(parentItem);
        Assert.assertEquals("1", dto.getCode());
        Assert.assertEquals("parentName", dto.getName());
        Assert.assertNull(dto.getParentCode());
        Assert.assertNull(dto.getLevel());
        Assert.assertEquals(0, dto.getChildren().size());
    }


}