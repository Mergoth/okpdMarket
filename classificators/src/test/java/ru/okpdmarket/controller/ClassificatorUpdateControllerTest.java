package ru.okpdmarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.okpdmarket.model.dto.ClassificatorLinkDto;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
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
        Classificator classificator1 = new Classificator();
        classificator1.setCode("OKPD");
        classificator1.setName("ОКПД");
        classificator1.setId("1");

        this.mockMvc.perform(put("/update").contentType(MediaType.APPLICATION_JSON).content(
                this.objectMapper.writeValueAsString(classificator1)))
                .andExpect(status().isOk())
                .andDo(document("put-classificator"));
    }

    @Test
    public void putClassificatorItem() throws Exception {
        String testItemString = "";
        this.mockMvc.perform(put("/update/OKPD/items").content(testItemString).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("put-classificator-item"));
    }

    @Test
    public void putClassificatorItemLink() throws Exception {
        ClassificatorLinkDto testLinks = new ClassificatorLinkDto();
        this.mockMvc.perform(put("/update/code/10/links", testLinks).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("put-classificator-item-links"));
    }

    @Test
    public void commit() throws Exception {
        this.mockMvc.perform(post("/update/commit").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("update-classificators-commit"));
    }


}
