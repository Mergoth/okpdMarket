package ru.okpdmarket.controller;

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
import ru.okpdmarket.service.ClassificatorService;
import ru.okpdmarket.service.impl.ClassificatorItemService;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Vladislav on 25.10.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ClassificatorControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation =
            new JUnitRestDocumentation("build/generated-snippets");
    @Autowired
    private ClassificatorService classificatorService;
    @Autowired
    private ClassificatorItemService itemService;

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUpInitial(){

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation).uris()
                        .withScheme("http")
                        .withHost("classificators"))
                .build();

        Classificator classificator1 = new Classificator();
        classificator1.setCode("OKPD");
        classificator1.setName("ОКПД");
        classificatorService.put(classificator1);
        val item11 = add("OKPD", "1", "Test", "");
        add("OKPD", "11", "TestLevel11", "1");
        add("OKPD", "12", "TestLevel12", "1");
        add("OKPD", "13", "TestLevel13", "1");
        add("OKPD", "121", "TestLevel121", "12");
        val item12 = add("OKPD", "2", "Test2", "");

        Classificator classificator2 = new Classificator();
        classificator2.setCode("tnvd");
        classificator2.setName("ТНВД");
        classificatorService.put(classificator2);

        val item21 = add("tnvd", "1", "TestTnvd", "");
        itemService.linkItem(item11, item21);
        itemService.linkItem(item21, item12);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getClassificatorTypes() throws Exception {
        this.mockMvc.perform(get("").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("classificators", preprocessResponse(prettyPrint()), responseFields(
                        fieldWithPath("[].code").description("English name of classificator type. To use in URL"),
                        fieldWithPath("[].name").description("Localized name of Classificator type"),
                        fieldWithPath("[].description").description("Description"))));
    }


    @Test
    public void getTopItems() throws Exception {
        this.mockMvc.perform(get("/OKPD").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("classificator-top-items", preprocessResponse(prettyPrint())));
    }

    @Test
    public void getItem() throws Exception {
        this.mockMvc.perform(get("/OKPD/12").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("classificator-item", preprocessResponse(prettyPrint())));
    }

    @Test
    public void search() throws Exception {
        this.mockMvc.perform(get("/OKPD/search?query=Test query").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("classificator-search-results", preprocessResponse(prettyPrint())));
    }


    private ClassificatorItem add(String clsId, String code, String name, String parentCode) {
        val item = new ClassificatorItem(code, name);
        item.setParentCode(parentCode);
        val list = itemService.addItem(clsId, item);
        return list.get(list.size() - 1);

    }
}