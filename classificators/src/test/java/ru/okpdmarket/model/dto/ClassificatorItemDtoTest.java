package ru.okpdmarket.model.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import ru.okpdmarket.model.ClassificatorItem;

import java.io.IOException;
import java.util.ArrayList;

public class ClassificatorItemDtoTest {

    @Test
    public void shouldDeserializeItem() {
        ObjectMapper objectMapper = new ObjectMapper();
        val item = new ClassificatorItem("12", "name", "");
        item.setParentCode("1");
        item.getProperties().put("integer", 1);
        item.getProperties().put("children", new ArrayList<ClassificatorItem>() {{
            ClassificatorItem child = new ClassificatorItem("c1", "childName");
            add(child);
        }});
        try {
            String json = objectMapper.writeValueAsString(item);
            System.out.println(json);
            ClassificatorItem itemWithOwner = new ObjectMapper().readValue(json, ClassificatorItem.class);
            Assert.assertEquals(item, itemWithOwner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDeserializeLink() {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassificatorLinkDto testLinks = new ClassificatorLinkDto();
        testLinks.setTargetClassificatorCode("2");
        testLinks.setTargetItemCode("22");
        try {
            String json = objectMapper.writeValueAsString(testLinks);
            System.out.println(json);
            ClassificatorLinkDto itemWithOwner = new ObjectMapper().readValue(json, ClassificatorLinkDto.class);
            Assert.assertEquals(testLinks, itemWithOwner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}