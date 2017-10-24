package artwork.web.rest;

import artwork.ArtWorkApp;

import artwork.domain.Experience;
import artwork.repository.ExperienceRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExperienceResource REST controller.
 *
 * @see ExperienceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtWorkApp.class)
public class ExperienceResourceIntTest {

    private static final Integer DEFAULT_START_YEAR = 1;
    private static final Integer UPDATED_START_YEAR = 2;

    private static final Integer DEFAULT_ENDING_YEAR = 1;
    private static final Integer UPDATED_ENDING_YEAR = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_COMPETITIONS = "AAAAAAAAAA";
    private static final String UPDATED_COMPETITIONS = "BBBBBBBBBB";

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExperienceMockMvc;

    private Experience experience;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExperienceResource experienceResource = new ExperienceResource(experienceRepository);
        this.restExperienceMockMvc = MockMvcBuilders.standaloneSetup(experienceResource)
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
    public static Experience createEntity(EntityManager em) {
        Experience experience = new Experience()
            .startYear(DEFAULT_START_YEAR)
            .endingYear(DEFAULT_ENDING_YEAR)
            .name(DEFAULT_NAME)
            .company(DEFAULT_COMPANY)
            .competitions(DEFAULT_COMPETITIONS);
        return experience;
    }

    @Before
    public void initTest() {
        experience = createEntity(em);
    }

    @Test
    @Transactional
    public void createExperience() throws Exception {
        int databaseSizeBeforeCreate = experienceRepository.findAll().size();

        // Create the Experience
        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experience)))
            .andExpect(status().isCreated());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeCreate + 1);
        Experience testExperience = experienceList.get(experienceList.size() - 1);
        assertThat(testExperience.getStartYear()).isEqualTo(DEFAULT_START_YEAR);
        assertThat(testExperience.getEndingYear()).isEqualTo(DEFAULT_ENDING_YEAR);
        assertThat(testExperience.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExperience.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testExperience.getCompetitions()).isEqualTo(DEFAULT_COMPETITIONS);
    }

    @Test
    @Transactional
    public void createExperienceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = experienceRepository.findAll().size();

        // Create the Experience with an existing ID
        experience.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experience)))
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExperiences() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList
        restExperienceMockMvc.perform(get("/api/experiences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experience.getId().intValue())))
            .andExpect(jsonPath("$.[*].startYear").value(hasItem(DEFAULT_START_YEAR)))
            .andExpect(jsonPath("$.[*].endingYear").value(hasItem(DEFAULT_ENDING_YEAR)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].competitions").value(hasItem(DEFAULT_COMPETITIONS.toString())));
    }

    @Test
    @Transactional
    public void getExperience() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get the experience
        restExperienceMockMvc.perform(get("/api/experiences/{id}", experience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(experience.getId().intValue()))
            .andExpect(jsonPath("$.startYear").value(DEFAULT_START_YEAR))
            .andExpect(jsonPath("$.endingYear").value(DEFAULT_ENDING_YEAR))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.competitions").value(DEFAULT_COMPETITIONS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExperience() throws Exception {
        // Get the experience
        restExperienceMockMvc.perform(get("/api/experiences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExperience() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);
        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();

        // Update the experience
        Experience updatedExperience = experienceRepository.findOne(experience.getId());
        updatedExperience
            .startYear(UPDATED_START_YEAR)
            .endingYear(UPDATED_ENDING_YEAR)
            .name(UPDATED_NAME)
            .company(UPDATED_COMPANY)
            .competitions(UPDATED_COMPETITIONS);

        restExperienceMockMvc.perform(put("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExperience)))
            .andExpect(status().isOk());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate);
        Experience testExperience = experienceList.get(experienceList.size() - 1);
        assertThat(testExperience.getStartYear()).isEqualTo(UPDATED_START_YEAR);
        assertThat(testExperience.getEndingYear()).isEqualTo(UPDATED_ENDING_YEAR);
        assertThat(testExperience.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExperience.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testExperience.getCompetitions()).isEqualTo(UPDATED_COMPETITIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingExperience() throws Exception {
        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();

        // Create the Experience

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExperienceMockMvc.perform(put("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experience)))
            .andExpect(status().isCreated());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExperience() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);
        int databaseSizeBeforeDelete = experienceRepository.findAll().size();

        // Get the experience
        restExperienceMockMvc.perform(delete("/api/experiences/{id}", experience.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Experience.class);
        Experience experience1 = new Experience();
        experience1.setId(1L);
        Experience experience2 = new Experience();
        experience2.setId(experience1.getId());
        assertThat(experience1).isEqualTo(experience2);
        experience2.setId(2L);
        assertThat(experience1).isNotEqualTo(experience2);
        experience1.setId(null);
        assertThat(experience1).isNotEqualTo(experience2);
    }
}
