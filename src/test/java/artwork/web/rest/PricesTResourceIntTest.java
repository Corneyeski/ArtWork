package artwork.web.rest;

import artwork.ArtWorkApp;

import artwork.domain.PricesT;
import artwork.repository.PricesTRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PricesTResource REST controller.
 *
 * @see PricesTResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtWorkApp.class)
public class PricesTResourceIntTest {

    private static final Integer DEFAULT_PHONE = 1;
    private static final Integer UPDATED_PHONE = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    @Autowired
    private PricesTRepository pricesTRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPricesTMockMvc;

    private PricesT pricesT;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PricesTResource pricesTResource = new PricesTResource(pricesTRepository);
        this.restPricesTMockMvc = MockMvcBuilders.standaloneSetup(pricesTResource)
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
    public static PricesT createEntity(EntityManager em) {
        PricesT pricesT = new PricesT()
            .phone(DEFAULT_PHONE)
            .name(DEFAULT_NAME)
            .city(DEFAULT_CITY);
        return pricesT;
    }

    @Before
    public void initTest() {
        pricesT = createEntity(em);
    }

    @Test
    @Transactional
    public void createPricesT() throws Exception {
        int databaseSizeBeforeCreate = pricesTRepository.findAll().size();

        // Create the PricesT
        restPricesTMockMvc.perform(post("/api/prices-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricesT)))
            .andExpect(status().isCreated());

        // Validate the PricesT in the database
        List<PricesT> pricesTList = pricesTRepository.findAll();
        assertThat(pricesTList).hasSize(databaseSizeBeforeCreate + 1);
        PricesT testPricesT = pricesTList.get(pricesTList.size() - 1);
        assertThat(testPricesT.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPricesT.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPricesT.getCity()).isEqualTo(DEFAULT_CITY);
    }

    @Test
    @Transactional
    public void createPricesTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pricesTRepository.findAll().size();

        // Create the PricesT with an existing ID
        pricesT.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPricesTMockMvc.perform(post("/api/prices-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricesT)))
            .andExpect(status().isBadRequest());

        // Validate the PricesT in the database
        List<PricesT> pricesTList = pricesTRepository.findAll();
        assertThat(pricesTList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPricesTS() throws Exception {
        // Initialize the database
        pricesTRepository.saveAndFlush(pricesT);

        // Get all the pricesTList
        restPricesTMockMvc.perform(get("/api/prices-ts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pricesT.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())));
    }

    @Test
    @Transactional
    public void getPricesT() throws Exception {
        // Initialize the database
        pricesTRepository.saveAndFlush(pricesT);

        // Get the pricesT
        restPricesTMockMvc.perform(get("/api/prices-ts/{id}", pricesT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pricesT.getId().intValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPricesT() throws Exception {
        // Get the pricesT
        restPricesTMockMvc.perform(get("/api/prices-ts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePricesT() throws Exception {
        // Initialize the database
        pricesTRepository.saveAndFlush(pricesT);
        int databaseSizeBeforeUpdate = pricesTRepository.findAll().size();

        // Update the pricesT
        PricesT updatedPricesT = pricesTRepository.findOne(pricesT.getId());
        updatedPricesT
            .phone(UPDATED_PHONE)
            .name(UPDATED_NAME)
            .city(UPDATED_CITY);

        restPricesTMockMvc.perform(put("/api/prices-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPricesT)))
            .andExpect(status().isOk());

        // Validate the PricesT in the database
        List<PricesT> pricesTList = pricesTRepository.findAll();
        assertThat(pricesTList).hasSize(databaseSizeBeforeUpdate);
        PricesT testPricesT = pricesTList.get(pricesTList.size() - 1);
        assertThat(testPricesT.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPricesT.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPricesT.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    @Transactional
    public void updateNonExistingPricesT() throws Exception {
        int databaseSizeBeforeUpdate = pricesTRepository.findAll().size();

        // Create the PricesT

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPricesTMockMvc.perform(put("/api/prices-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricesT)))
            .andExpect(status().isCreated());

        // Validate the PricesT in the database
        List<PricesT> pricesTList = pricesTRepository.findAll();
        assertThat(pricesTList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePricesT() throws Exception {
        // Initialize the database
        pricesTRepository.saveAndFlush(pricesT);
        int databaseSizeBeforeDelete = pricesTRepository.findAll().size();

        // Get the pricesT
        restPricesTMockMvc.perform(delete("/api/prices-ts/{id}", pricesT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PricesT> pricesTList = pricesTRepository.findAll();
        assertThat(pricesTList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PricesT.class);
        PricesT pricesT1 = new PricesT();
        pricesT1.setId(1L);
        PricesT pricesT2 = new PricesT();
        pricesT2.setId(pricesT1.getId());
        assertThat(pricesT1).isEqualTo(pricesT2);
        pricesT2.setId(2L);
        assertThat(pricesT1).isNotEqualTo(pricesT2);
        pricesT1.setId(null);
        assertThat(pricesT1).isNotEqualTo(pricesT2);
    }
}
