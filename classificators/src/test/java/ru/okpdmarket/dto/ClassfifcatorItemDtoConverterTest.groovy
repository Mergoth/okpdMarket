package ru.okpdmarket.dto

import org.junit.Assert
import org.junit.Test
import ru.okpdmarket.model.ClassificatorItem

/**
 * Created by Vladislav on 06.11.2016.
 */
class ClassfifcatorItemDtoConverterTest extends GroovyTestCase {


    void testToDtoExtended() throws Exception {
        ClassificatorItem parentItem = createClassificatorItems()

        def dto = ClassificatorItemDto.Converter.toDto(parentItem, true)
        assertEquals("1", dto.getCode())
        assertEquals("parentName", dto.getName())
        assertEquals(Integer.valueOf(0), dto.getLevel())
        assertEquals("item1Name", dto.getChildren().get(0).getName())
        assertEquals(2, dto.getChildren().size())
        assertEquals(0, dto.getChildren().get(1).getChildren().size())
    }


    void testToDtoSimple() throws Exception {
        ClassificatorItem parentItem = createClassificatorItems()

        def dto = ClassificatorItemDto.Converter.toDto(parentItem)
        assertEquals("1", dto.getCode())
        assertEquals("parentName", dto.getName())
        assertNull(dto.getParentCode())
        assertNull(dto.getLevel())
        assertEquals(0, dto.getChildren().size())
    }

    private ClassificatorItem createClassificatorItems() {
        ClassificatorItem parentItem = new ClassificatorItem(null, "1", "parentName")
        ClassificatorItem item1 = new ClassificatorItem(parentItem, "1.1", "item1Name")
        ClassificatorItem item2 = new ClassificatorItem(parentItem, "1.2", "item2Name")
        ClassificatorItem item21 = new ClassificatorItem(parentItem, "1.2.1", "item21Name")
        item2.setChildren(Collections.singletonList(item21))
        parentItem.setChildren(Arrays.asList(item1, item2))
        parentItem
    }
}
