package ru.okpdmarket.model.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by vladislav on 12/01/2017.
 */
public class ClassificatorItemDtoTest {

    @Test
    public void shouldDeserialize() {
        ObjectMapper objectMapper = new ObjectMapper();
        /* val item = new ClassificatorItem();
        item.setCode("code");
        item.setName("name");
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
        }*/

        ClassificatorLinkDto testLinks = new ClassificatorLinkDto();
        testLinks.setTargetClassificatorId("2");
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