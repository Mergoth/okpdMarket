package ru.okpdmarket.controller;

import lombok.val;
import org.junit.*;
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
import ru.okpdmarket.repository.ClassificatorRepository;

import java.util.Collections;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
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
    private
    ClassificatorRepository classificatorRepository;
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

        Classificator classificator1 = new Classificator("1", "ОКПД");
        classificator1.setId("1");
        val item11 = classificator1.add("1", "Test");
        classificator1.add("11", "TestLevel11", "1");
        classificator1.add("12", "TestLevel12", "1");
        classificator1.add("13", "TestLevel13", "1");
        classificator1.add("121", "TestLevel121", "12");
        val item12 = classificator1.add("2", "Test2");

        Classificator classificator2 = new Classificator("2", "ТНВД");
        classificator2.setId("2");
        val item21 = classificator2.add("1", "TestTnvd");
        item21.linkItem(item11);
        item21.linkItem(item12);

        this.classificatorRepository.updateClassificators(Collections.singletonList(classificator1));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getClassificatorTypes() throws Exception {
        this.mockMvc.perform(get("").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("classificators", preprocessResponse(prettyPrint()), responseFields(
                        fieldWithPath("[].id").description("Id of classificator type"),
                        fieldWithPath("[].code").description("English name of classificator type. To use in URL"),
                        fieldWithPath("[].name").description("Localized name of Classificator type"),
                        fieldWithPath("[].description").description("Description"))));
    }


    @Test
    public void getTopItems() throws Exception {
        this.mockMvc.perform(get("/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("classificator-top-items", preprocessResponse(prettyPrint())));
    }

    @Test
    public void getItem() throws Exception {
        this.mockMvc.perform(get("/1/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("classificator-item", preprocessResponse(prettyPrint())));
    }

    @Test
    @Ignore
    public void search() throws Exception {
        this.mockMvc.perform(get("/1/search?query=Test query").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("classificator-search-results", preprocessResponse(prettyPrint())));
    }

    @Ignore
    @Test
    public void putClassificator() throws Exception {
        Classificator testClassificator = new Classificator("okpd", "ОКПД");
        this.mockMvc.perform(put("/update", testClassificator).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("classificators-put"));
    }

    @Ignore
    @Test
    public void exportClassificators() throws Exception {

    }


}