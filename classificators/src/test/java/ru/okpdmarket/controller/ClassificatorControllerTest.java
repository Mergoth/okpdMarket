package ru.okpdmarket.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.okpdmarket.model.Classificator;
import ru.okpdmarket.repository.ClassificatorRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Vladislav on 25.10.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ClassificatorControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation =
            new JUnitRestDocumentation("build/generated-snippets");
    @Autowired
    ClassificatorRepository classificatorRepository;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
        Classificator classificator = new Classificator("code", "test");
        classificator.add("1", "Test");
        classificator.add("1.1", "TestLevel11", "1");
        classificator.add("1.2", "TestLevel12", "1");
        classificator.add("1.3", "TestLevel13", "1");
        classificator.add("1.2.1", "TestLevel121", "1.2");
        classificator.add("2", "Test2");
        this.classificatorRepository.updateClassificators(Collections.singletonList(classificator));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getClassificatorTypes() throws Exception {
        this.mockMvc.perform(get("/classificators").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("classificators", preprocessResponse(prettyPrint())));
    }


    @Test
    public void getTopItems() throws Exception {
        this.mockMvc.perform(get("/classificators/code").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("classificator-top-items", preprocessResponse(prettyPrint())));
    }

    @Test
    public void getItem() throws Exception {
        this.mockMvc.perform(get("/classificators/code/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("classificator-item", preprocessResponse(prettyPrint())));
    }

    @Test
    public void search() throws Exception {
        this.mockMvc.perform(get("/classificators/code/search?query=Test query").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("classificator-search-results", preprocessResponse(prettyPrint())));
    }

    @Ignore
    @Test
    public void putClassificator() throws Exception {
        Classificator testClassificator = new Classificator("okpd", "ОКПД");
        this.mockMvc.perform(put("/update/classificators", testClassificator).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("classificators-put"));
    }

    @Ignore
    @Test
    public void exportClassificators() throws Exception {

    }

}