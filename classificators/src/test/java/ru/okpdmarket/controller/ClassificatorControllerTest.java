package ru.okpdmarket.controller;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
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

import java.io.IOException;
import java.util.Collections;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
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
    ClassificatorRepository classificatorRepository;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;


    public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS okpd WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

    public static final String KEYSPACE_ACTIVATE_QUERY = "USE okpd;";

    public static final String CLASSIFICATOR_TABLE_NAME = "classificator";

    public static final String query = "create table okpd.classificator (id uuid primary key, name text, code text, description text);";

    @BeforeClass
    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        final Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").withPort(9142).build();
        final Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);
       session.execute(query);
        Thread.sleep(5000);
    }

    @Before
    public void createTable() throws InterruptedException, TTransportException, ConfigurationException, IOException {
      //adminTemplate.createTable(true, CqlIdentifier.cqlId(CLASSIFICATOR_TABLE_NAME), Classificator.class, new HashMap<String, Object>());
    }

    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
        Classificator classificator = new Classificator();
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
                .andDo(document("classificators"));
    }


    @Test
    public void getTopItems() throws Exception {
        this.mockMvc.perform(get("/classificators/code").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("classificator-top-items"));
    }

    @Test
    public void getItems() throws Exception {
        this.mockMvc.perform(get("/classificators/code/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("classificator-items"));
    }

    @Test
    public void search() throws Exception {
        this.mockMvc.perform(get("/classificators/code/search?query=Test query").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("classificator-search-results"));
    }

    @Ignore
    @Test
    public void putClassificator() throws Exception {
        Classificator testClassificator = new Classificator();
        this.mockMvc.perform(put("/update/classificators", testClassificator).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("classificators-put"));
    }

    @Ignore
    @Test
    public void exportClassificators() throws Exception {

    }

    @AfterClass
    public static void stopCassandraEmbedded() {
        //EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }

}