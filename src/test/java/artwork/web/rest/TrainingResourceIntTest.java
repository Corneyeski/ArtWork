package artwork.web.rest;

import artwork.ArtWorkApp;

import artwork.domain.Training;
import artwork.repository.TrainingRepository;
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
 * Test class for the TrainingResource REST controller.
 *
 * @see TrainingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtWorkApp.class)
public class TrainingResourceIntTest {

    private static final Integer DEFAULT_START_YEAR = 1;
    private static final Integer UPDATED_START_YEAR = 2;

    private static final Integer DEFAULT_ENDING_YEAR = 1;
    private static final Integer UPDATED_ENDING_YEAR = 2;

    private static final String DEFAULT_DEGREE = "AAAAAAAAAA";
    private static final String UPDATED_DEGREE = "BBBBBBBBBB";

    private static final String DEFAULT_STUDY_CENTER = "AAAAAAAAAA";
    private static final String UPDATED_STUDY_CENTER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COMPETITIONS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMPETITIONS = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_COMPETITIONS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMPETITIONS_CONTENT_TYPE = "image/png";

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTrainingMockMvc;

    private Training training;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrainingResource trainingResource = new TrainingResource(trainingRepository);
        this.restTrainingMockMvc = MockMvcBuilders.standaloneSetup(trainingResource)
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
    public static Training createEntity(EntityManager em) {
        Training training = new Training()
            .startYear(DEFAULT_START_YEAR)
            .endingYear(DEFAULT_ENDING_YEAR)
            .degree(DEFAULT_DEGREE)
            .studyCenter(DEFAULT_STUDY_CENTER)
            .competitions(DEFAULT_COMPETITIONS)
            .competitionsContentType(DEFAULT_COMPETITIONS_CONTENT_TYPE);
        return training;
    }

    @Before
    public void initTest() {
        training = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraining() throws Exception {
        int databaseSizeBeforeCreate = trainingRepository.findAll().size();

        // Create the Training
        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(training)))
            .andExpect(status().isCreated());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeCreate + 1);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getStartYear()).isEqualTo(DEFAULT_START_YEAR);
        assertThat(testTraining.getEndingYear()).isEqualTo(DEFAULT_ENDING_YEAR);
        assertThat(testTraining.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testTraining.getStudyCenter()).isEqualTo(DEFAULT_STUDY_CENTER);
        assertThat(testTraining.getCompetitions()).isEqualTo(DEFAULT_COMPETITIONS);
        assertThat(testTraining.getCompetitionsContentType()).isEqualTo(DEFAULT_COMPETITIONS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createTrainingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingRepository.findAll().size();

        // Create the Training with an existing ID
        training.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(training)))
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrainings() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList
        restTrainingMockMvc.perform(get("/api/trainings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(training.getId().intValue())))
            .andExpect(jsonPath("$.[*].startYear").value(hasItem(DEFAULT_START_YEAR)))
            .andExpect(jsonPath("$.[*].endingYear").value(hasItem(DEFAULT_ENDING_YEAR)))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE.toString())))
            .andExpect(jsonPath("$.[*].studyCenter").value(hasItem(DEFAULT_STUDY_CENTER.toString())))
            .andExpect(jsonPath("$.[*].competitionsContentType").value(hasItem(DEFAULT_COMPETITIONS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].competitions").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMPETITIONS))));
    }

    @Test
    @Transactional
    public void getTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get the training
        restTrainingMockMvc.perform(get("/api/trainings/{id}", training.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(training.getId().intValue()))
            .andExpect(jsonPath("$.startYear").value(DEFAULT_START_YEAR))
            .andExpect(jsonPath("$.endingYear").value(DEFAULT_ENDING_YEAR))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE.toString()))
            .andExpect(jsonPath("$.studyCenter").value(DEFAULT_STUDY_CENTER.toString()))
            .andExpect(jsonPath("$.competitionsContentType").value(DEFAULT_COMPETITIONS_CONTENT_TYPE))
            .andExpect(jsonPath("$.competitions").value(Base64Utils.encodeToString(DEFAULT_COMPETITIONS)));
    }

    @Test
    @Transactional
    public void getNonExistingTraining() throws Exception {
        // Get the training
        restTrainingMockMvc.perform(get("/api/trainings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Update the training
        Training updatedTraining = trainingRepository.findOne(training.getId());
        updatedTraining
            .startYear(UPDATED_START_YEAR)
            .endingYear(UPDATED_ENDING_YEAR)
            .degree(UPDATED_DEGREE)
            .studyCenter(UPDATED_STUDY_CENTER)
            .competitions(UPDATED_COMPETITIONS)
            .competitionsContentType(UPDATED_COMPETITIONS_CONTENT_TYPE);

        restTrainingMockMvc.perform(put("/api/trainings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTraining)))
            .andExpect(status().isOk());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getStartYear()).isEqualTo(UPDATED_START_YEAR);
        assertThat(testTraining.getEndingYear()).isEqualTo(UPDATED_ENDING_YEAR);
        assertThat(testTraining.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testTraining.getStudyCenter()).isEqualTo(UPDATED_STUDY_CENTER);
        assertThat(testTraining.getCompetitions()).isEqualTo(UPDATED_COMPETITIONS);
        assertThat(testTraining.getCompetitionsContentType()).isEqualTo(UPDATED_COMPETITIONS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Create the Training

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTrainingMockMvc.perform(put("/api/trainings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(training)))
            .andExpect(status().isCreated());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);
        int databaseSizeBeforeDelete = trainingRepository.findAll().size();

        // Get the training
        restTrainingMockMvc.perform(delete("/api/trainings/{id}", training.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Training.class);
        Training training1 = new Training();
        training1.setId(1L);
        Training training2 = new Training();
        training2.setId(training1.getId());
        assertThat(training1).isEqualTo(training2);
        training2.setId(2L);
        assertThat(training1).isNotEqualTo(training2);
        training1.setId(null);
        assertThat(training1).isNotEqualTo(training2);
    }
}
