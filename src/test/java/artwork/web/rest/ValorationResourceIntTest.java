package artwork.web.rest;

import artwork.ArtWorkApp;

import artwork.domain.Valoration;
import artwork.repository.ValorationRepository;
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
 * Test class for the ValorationResource REST controller.
 *
 * @see ValorationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtWorkApp.class)
public class ValorationResourceIntTest {

    private static final Double DEFAULT_MARK = 0D;
    private static final Double UPDATED_MARK = 1D;

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ValorationRepository valorationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restValorationMockMvc;

    private Valoration valoration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ValorationResource valorationResource = new ValorationResource(valorationRepository);
        this.restValorationMockMvc = MockMvcBuilders.standaloneSetup(valorationResource)
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
    public static Valoration createEntity(EntityManager em) {
        Valoration valoration = new Valoration()
            .mark(DEFAULT_MARK)
            .time(DEFAULT_TIME);
        return valoration;
    }

    @Before
    public void initTest() {
        valoration = createEntity(em);
    }

    @Test
    @Transactional
    public void createValoration() throws Exception {
        int databaseSizeBeforeCreate = valorationRepository.findAll().size();

        // Create the Valoration
        restValorationMockMvc.perform(post("/api/valorations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoration)))
            .andExpect(status().isCreated());

        // Validate the Valoration in the database
        List<Valoration> valorationList = valorationRepository.findAll();
        assertThat(valorationList).hasSize(databaseSizeBeforeCreate + 1);
        Valoration testValoration = valorationList.get(valorationList.size() - 1);
        assertThat(testValoration.getMark()).isEqualTo(DEFAULT_MARK);
        assertThat(testValoration.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createValorationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = valorationRepository.findAll().size();

        // Create the Valoration with an existing ID
        valoration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValorationMockMvc.perform(post("/api/valorations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoration)))
            .andExpect(status().isBadRequest());

        // Validate the Valoration in the database
        List<Valoration> valorationList = valorationRepository.findAll();
        assertThat(valorationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMarkIsRequired() throws Exception {
        int databaseSizeBeforeTest = valorationRepository.findAll().size();
        // set the field null
        valoration.setMark(null);

        // Create the Valoration, which fails.

        restValorationMockMvc.perform(post("/api/valorations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoration)))
            .andExpect(status().isBadRequest());

        List<Valoration> valorationList = valorationRepository.findAll();
        assertThat(valorationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllValorations() throws Exception {
        // Initialize the database
        valorationRepository.saveAndFlush(valoration);

        // Get all the valorationList
        restValorationMockMvc.perform(get("/api/valorations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valoration.getId().intValue())))
            .andExpect(jsonPath("$.[*].mark").value(hasItem(DEFAULT_MARK.doubleValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))));
    }

    @Test
    @Transactional
    public void getValoration() throws Exception {
        // Initialize the database
        valorationRepository.saveAndFlush(valoration);

        // Get the valoration
        restValorationMockMvc.perform(get("/api/valorations/{id}", valoration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valoration.getId().intValue()))
            .andExpect(jsonPath("$.mark").value(DEFAULT_MARK.doubleValue()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingValoration() throws Exception {
        // Get the valoration
        restValorationMockMvc.perform(get("/api/valorations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValoration() throws Exception {
        // Initialize the database
        valorationRepository.saveAndFlush(valoration);
        int databaseSizeBeforeUpdate = valorationRepository.findAll().size();

        // Update the valoration
        Valoration updatedValoration = valorationRepository.findOne(valoration.getId());
        updatedValoration
            .mark(UPDATED_MARK)
            .time(UPDATED_TIME);

        restValorationMockMvc.perform(put("/api/valorations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedValoration)))
            .andExpect(status().isOk());

        // Validate the Valoration in the database
        List<Valoration> valorationList = valorationRepository.findAll();
        assertThat(valorationList).hasSize(databaseSizeBeforeUpdate);
        Valoration testValoration = valorationList.get(valorationList.size() - 1);
        assertThat(testValoration.getMark()).isEqualTo(UPDATED_MARK);
        assertThat(testValoration.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingValoration() throws Exception {
        int databaseSizeBeforeUpdate = valorationRepository.findAll().size();

        // Create the Valoration

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restValorationMockMvc.perform(put("/api/valorations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoration)))
            .andExpect(status().isCreated());

        // Validate the Valoration in the database
        List<Valoration> valorationList = valorationRepository.findAll();
        assertThat(valorationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteValoration() throws Exception {
        // Initialize the database
        valorationRepository.saveAndFlush(valoration);
        int databaseSizeBeforeDelete = valorationRepository.findAll().size();

        // Get the valoration
        restValorationMockMvc.perform(delete("/api/valorations/{id}", valoration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Valoration> valorationList = valorationRepository.findAll();
        assertThat(valorationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Valoration.class);
        Valoration valoration1 = new Valoration();
        valoration1.setId(1L);
        Valoration valoration2 = new Valoration();
        valoration2.setId(valoration1.getId());
        assertThat(valoration1).isEqualTo(valoration2);
        valoration2.setId(2L);
        assertThat(valoration1).isNotEqualTo(valoration2);
        valoration1.setId(null);
        assertThat(valoration1).isNotEqualTo(valoration2);
    }
}
