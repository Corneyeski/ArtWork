package artwork.web.rest;

import artwork.ArtWorkApp;

import artwork.domain.Connection;
import artwork.repository.ConnectionRepository;
import artwork.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static artwork.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConnectionResource REST controller.
 *
 * @see ConnectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtWorkApp.class)
public class ConnectionResourceIntTest {

    private static final Boolean DEFAULT_ACEPTED = false;
    private static final Boolean UPDATED_ACEPTED = true;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConnectionMockMvc;

    private Connection connection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConnectionResource connectionResource = new ConnectionResource(connectionRepository);
        this.restConnectionMockMvc = MockMvcBuilders.standaloneSetup(connectionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Connection createEntity(EntityManager em) {
        Connection connection = new Connection()
            .acepted(DEFAULT_ACEPTED)
            .title(DEFAULT_TITLE)
            .message(DEFAULT_MESSAGE)
            .time(DEFAULT_TIME);
        return connection;
    }

    @Before
    public void initTest() {
        connection = createEntity(em);
    }

    @Test
    @Transactional
    public void createConnection() throws Exception {
        int databaseSizeBeforeCreate = connectionRepository.findAll().size();

        // Create the Connection
        restConnectionMockMvc.perform(post("/api/connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connection)))
            .andExpect(status().isCreated());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeCreate + 1);
        Connection testConnection = connectionList.get(connectionList.size() - 1);
        assertThat(testConnection.isAcepted()).isEqualTo(DEFAULT_ACEPTED);
        assertThat(testConnection.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testConnection.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testConnection.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createConnectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = connectionRepository.findAll().size();

        // Create the Connection with an existing ID
        connection.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConnectionMockMvc.perform(post("/api/connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connection)))
            .andExpect(status().isBadRequest());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConnections() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList
        restConnectionMockMvc.perform(get("/api/connections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connection.getId().intValue())))
            .andExpect(jsonPath("$.[*].acepted").value(hasItem(DEFAULT_ACEPTED.booleanValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))));
    }

    @Test
    @Transactional
    public void getConnection() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get the connection
        restConnectionMockMvc.perform(get("/api/connections/{id}", connection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(connection.getId().intValue()))
            .andExpect(jsonPath("$.acepted").value(DEFAULT_ACEPTED.booleanValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingConnection() throws Exception {
        // Get the connection
        restConnectionMockMvc.perform(get("/api/connections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConnection() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);
        int databaseSizeBeforeUpdate = connectionRepository.findAll().size();

        // Update the connection
        Connection updatedConnection = connectionRepository.findOne(connection.getId());
        updatedConnection
            .acepted(UPDATED_ACEPTED)
            .title(UPDATED_TITLE)
            .message(UPDATED_MESSAGE)
            .time(UPDATED_TIME);

        restConnectionMockMvc.perform(put("/api/connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConnection)))
            .andExpect(status().isOk());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeUpdate);
        Connection testConnection = connectionList.get(connectionList.size() - 1);
        assertThat(testConnection.isAcepted()).isEqualTo(UPDATED_ACEPTED);
        assertThat(testConnection.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testConnection.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testConnection.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingConnection() throws Exception {
        int databaseSizeBeforeUpdate = connectionRepository.findAll().size();

        // Create the Connection

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConnectionMockMvc.perform(put("/api/connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connection)))
            .andExpect(status().isCreated());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConnection() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);
        int databaseSizeBeforeDelete = connectionRepository.findAll().size();

        // Get the connection
        restConnectionMockMvc.perform(delete("/api/connections/{id}", connection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Connection.class);
        Connection connection1 = new Connection();
        connection1.setId(1L);
        Connection connection2 = new Connection();
        connection2.setId(connection1.getId());
        assertThat(connection1).isEqualTo(connection2);
        connection2.setId(2L);
        assertThat(connection1).isNotEqualTo(connection2);
        connection1.setId(null);
        assertThat(connection1).isNotEqualTo(connection2);
    }
}
