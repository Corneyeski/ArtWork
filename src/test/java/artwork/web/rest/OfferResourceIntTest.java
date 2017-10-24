package artwork.web.rest;

import artwork.ArtWorkApp;

import artwork.domain.Offer;
import artwork.repository.OfferRepository;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the OfferResource REST controller.
 *
 * @see OfferResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtWorkApp.class)
public class OfferResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_SALARY = 1D;
    private static final Double UPDATED_SALARY = 2D;

    private static final Double DEFAULT_DURATION = 1D;
    private static final Double UPDATED_DURATION = 2D;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT = "BBBBBBBBBB";

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOfferMockMvc;

    private Offer offer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OfferResource offerResource = new OfferResource(offerRepository);
        this.restOfferMockMvc = MockMvcBuilders.standaloneSetup(offerResource)
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
    public static Offer createEntity(EntityManager em) {
        Offer offer = new Offer()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .time(DEFAULT_TIME)
            .salary(DEFAULT_SALARY)
            .duration(DEFAULT_DURATION)
            .status(DEFAULT_STATUS)
            .tags(DEFAULT_TAGS)
            .location(DEFAULT_LOCATION)
            .contract(DEFAULT_CONTRACT);
        return offer;
    }

    @Before
    public void initTest() {
        offer = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffer() throws Exception {
        int databaseSizeBeforeCreate = offerRepository.findAll().size();

        // Create the Offer
        restOfferMockMvc.perform(post("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offer)))
            .andExpect(status().isCreated());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate + 1);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOffer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOffer.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testOffer.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testOffer.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testOffer.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOffer.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testOffer.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testOffer.getContract()).isEqualTo(DEFAULT_CONTRACT);
    }

    @Test
    @Transactional
    public void createOfferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offerRepository.findAll().size();

        // Create the Offer with an existing ID
        offer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferMockMvc.perform(post("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offer)))
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOffers() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList
        restOfferMockMvc.perform(get("/api/offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].contract").value(hasItem(DEFAULT_CONTRACT.toString())));
    }

    @Test
    @Transactional
    public void getOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get the offer
        restOfferMockMvc.perform(get("/api/offers/{id}", offer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(offer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.doubleValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.contract").value(DEFAULT_CONTRACT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOffer() throws Exception {
        // Get the offer
        restOfferMockMvc.perform(get("/api/offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer
        Offer updatedOffer = offerRepository.findOne(offer.getId());
        updatedOffer
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .time(UPDATED_TIME)
            .salary(UPDATED_SALARY)
            .duration(UPDATED_DURATION)
            .status(UPDATED_STATUS)
            .tags(UPDATED_TAGS)
            .location(UPDATED_LOCATION)
            .contract(UPDATED_CONTRACT);

        restOfferMockMvc.perform(put("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOffer)))
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOffer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOffer.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testOffer.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testOffer.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testOffer.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOffer.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testOffer.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testOffer.getContract()).isEqualTo(UPDATED_CONTRACT);
    }

    @Test
    @Transactional
    public void updateNonExistingOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Create the Offer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOfferMockMvc.perform(put("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offer)))
            .andExpect(status().isCreated());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);
        int databaseSizeBeforeDelete = offerRepository.findAll().size();

        // Get the offer
        restOfferMockMvc.perform(delete("/api/offers/{id}", offer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Offer.class);
        Offer offer1 = new Offer();
        offer1.setId(1L);
        Offer offer2 = new Offer();
        offer2.setId(offer1.getId());
        assertThat(offer1).isEqualTo(offer2);
        offer2.setId(2L);
        assertThat(offer1).isNotEqualTo(offer2);
        offer1.setId(null);
        assertThat(offer1).isNotEqualTo(offer2);
    }
}
