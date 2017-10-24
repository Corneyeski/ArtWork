package artwork.web.rest;

import artwork.ArtWorkApp;

import artwork.domain.Blocked;
import artwork.repository.BlockedRepository;
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
 * Test class for the BlockedResource REST controller.
 *
 * @see BlockedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtWorkApp.class)
public class BlockedResourceIntTest {

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BlockedRepository blockedRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBlockedMockMvc;

    private Blocked blocked;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BlockedResource blockedResource = new BlockedResource(blockedRepository);
        this.restBlockedMockMvc = MockMvcBuilders.standaloneSetup(blockedResource)
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
    public static Blocked createEntity(EntityManager em) {
        Blocked blocked = new Blocked()
            .time(DEFAULT_TIME);
        return blocked;
    }

    @Before
    public void initTest() {
        blocked = createEntity(em);
    }

    @Test
    @Transactional
    public void createBlocked() throws Exception {
        int databaseSizeBeforeCreate = blockedRepository.findAll().size();

        // Create the Blocked
        restBlockedMockMvc.perform(post("/api/blockeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blocked)))
            .andExpect(status().isCreated());

        // Validate the Blocked in the database
        List<Blocked> blockedList = blockedRepository.findAll();
        assertThat(blockedList).hasSize(databaseSizeBeforeCreate + 1);
        Blocked testBlocked = blockedList.get(blockedList.size() - 1);
        assertThat(testBlocked.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createBlockedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blockedRepository.findAll().size();

        // Create the Blocked with an existing ID
        blocked.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlockedMockMvc.perform(post("/api/blockeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blocked)))
            .andExpect(status().isBadRequest());

        // Validate the Blocked in the database
        List<Blocked> blockedList = blockedRepository.findAll();
        assertThat(blockedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBlockeds() throws Exception {
        // Initialize the database
        blockedRepository.saveAndFlush(blocked);

        // Get all the blockedList
        restBlockedMockMvc.perform(get("/api/blockeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blocked.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))));
    }

    @Test
    @Transactional
    public void getBlocked() throws Exception {
        // Initialize the database
        blockedRepository.saveAndFlush(blocked);

        // Get the blocked
        restBlockedMockMvc.perform(get("/api/blockeds/{id}", blocked.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(blocked.getId().intValue()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBlocked() throws Exception {
        // Get the blocked
        restBlockedMockMvc.perform(get("/api/blockeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlocked() throws Exception {
        // Initialize the database
        blockedRepository.saveAndFlush(blocked);
        int databaseSizeBeforeUpdate = blockedRepository.findAll().size();

        // Update the blocked
        Blocked updatedBlocked = blockedRepository.findOne(blocked.getId());
        updatedBlocked
            .time(UPDATED_TIME);

        restBlockedMockMvc.perform(put("/api/blockeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBlocked)))
            .andExpect(status().isOk());

        // Validate the Blocked in the database
        List<Blocked> blockedList = blockedRepository.findAll();
        assertThat(blockedList).hasSize(databaseSizeBeforeUpdate);
        Blocked testBlocked = blockedList.get(blockedList.size() - 1);
        assertThat(testBlocked.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBlocked() throws Exception {
        int databaseSizeBeforeUpdate = blockedRepository.findAll().size();

        // Create the Blocked

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBlockedMockMvc.perform(put("/api/blockeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blocked)))
            .andExpect(status().isCreated());

        // Validate the Blocked in the database
        List<Blocked> blockedList = blockedRepository.findAll();
        assertThat(blockedList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBlocked() throws Exception {
        // Initialize the database
        blockedRepository.saveAndFlush(blocked);
        int databaseSizeBeforeDelete = blockedRepository.findAll().size();

        // Get the blocked
        restBlockedMockMvc.perform(delete("/api/blockeds/{id}", blocked.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Blocked> blockedList = blockedRepository.findAll();
        assertThat(blockedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Blocked.class);
        Blocked blocked1 = new Blocked();
        blocked1.setId(1L);
        Blocked blocked2 = new Blocked();
        blocked2.setId(blocked1.getId());
        assertThat(blocked1).isEqualTo(blocked2);
        blocked2.setId(2L);
        assertThat(blocked1).isNotEqualTo(blocked2);
        blocked1.setId(null);
        assertThat(blocked1).isNotEqualTo(blocked2);
    }
}
