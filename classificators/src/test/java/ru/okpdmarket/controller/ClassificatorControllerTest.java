package ru.okpdmarket.controller;

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
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        Classificator classificator = new Classificator("code", "test");
        classificator.add("1", "Test");
        classificator.add("11", "TestLevel11", "1");
        classificator.add("12", "TestLevel12", "1");
        classificator.add("13", "TestLevel13", "1");
        classificator.add("121", "TestLevel121", "12");
        classificator.add("2", "Test2");
        this.classificatorRepository.updateClassificators(Collections.singletonList(classificator));
    }

    @After
    public void tearDown() throws Exception {
       // mongod.stop();
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