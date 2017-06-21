package ru.okpdmarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.model.ClassificatorItem;
import ru.okpdmarket.model.dto.ClassificatorLinkDto;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by vladislav on 31/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ClassificatorUpdateControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation =
            new JUnitRestDocumentation("build/generated-snippets");

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUpInitial() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation).uris()
                        .withScheme("http")
                        .withHost("classificators"))
                .build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void putClassificator() throws Exception {
        Classificator classificator1 = createClassificator("okpd", "ОКПД");

        this.mockMvc.perform(put("/update").contentType(MediaType.APPLICATION_JSON).content(
                this.objectMapper.writeValueAsString(classificator1)))
                .andExpect(status().isOk())
                .andDo(document("put-classificator", preprocessResponse(prettyPrint())));
    }

    @Test
    public void putClassificatorItem() throws Exception {

        Classificator classificator1 = createClassificator("okpd", "ОКПД");
        this.mockMvc.perform(put("/update").contentType(MediaType.APPLICATION_JSON).content(
                this.objectMapper.writeValueAsString(classificator1)));

        ClassificatorItem item = createClassificatorItem("11", "code", "name", "testNotes");
        this.mockMvc.perform(put("/update/okpd/items").contentType(MediaType.APPLICATION_JSON).content(
                this.objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andDo(document("put-classificator-item", preprocessResponse(prettyPrint())));
    }

    @Test
    public void putClassificatorItemLink() throws Exception {
        Classificator classificator1 = createClassificator("okpd", "ОКПД");
        this.mockMvc.perform(put("/update").contentType(MediaType.APPLICATION_JSON).content(
                this.objectMapper.writeValueAsString(classificator1)));

        ClassificatorItem item1 = createClassificatorItem("11", "111", "name", "testNotes");
        this.mockMvc.perform(put("/update/okpd/items").contentType(MediaType.APPLICATION_JSON).content(
                this.objectMapper.writeValueAsString(item1)));

        Classificator classificator2 = createClassificator("tnvd", "ТНВД");
        this.mockMvc.perform(put("/update").contentType(MediaType.APPLICATION_JSON).content(
                this.objectMapper.writeValueAsString(classificator2)));

        ClassificatorItem item2 = createClassificatorItem("22", "222", "name", "testNotes");
        this.mockMvc.perform(put("/update/tnvd/items").contentType(MediaType.APPLICATION_JSON).content(
                this.objectMapper.writeValueAsString(item2)));


        ClassificatorLinkDto testLinks = new ClassificatorLinkDto();
        testLinks.setTargetClassificatorCode("tnvd");
        testLinks.setTargetItemCode("222");
        this.mockMvc.perform(put("/update/okpd/111/links").contentType(MediaType.APPLICATION_JSON).content(
                this.objectMapper.writeValueAsString(testLinks)))
                .andExpect(status().isOk())
                .andDo(document("put-classificator-item-links", preprocessResponse(prettyPrint())));
    }

    @Test
    public void commit() throws Exception {
        this.mockMvc.perform(post("/update/commit").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("update-classificators-commit", preprocessResponse(prettyPrint())));
    }

    private Classificator createClassificator(String code, String name) {
        Classificator classificator1 = new Classificator();
        classificator1.setCode(code);
        classificator1.setName(name);
        return classificator1;
    }

    private ClassificatorItem createClassificatorItem(String paerntCode, String code, String name, String notes) {
        val item = new ClassificatorItem(code, name, notes);
        item.setParentCode(paerntCode);
        return item;
    }
}
