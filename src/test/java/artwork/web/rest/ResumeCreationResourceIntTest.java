package artwork.web.rest;

import artwork.ArtWorkApp;

import artwork.domain.ResumeCreation;
import artwork.repository.ResumeCreationRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ResumeCreationResource REST controller.
 *
 * @see ResumeCreationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtWorkApp.class)
public class ResumeCreationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_PHONE = 1;
    private static final Integer UPDATED_PHONE = 2;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SKILLS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SKILLS = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_SKILLS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SKILLS_CONTENT_TYPE = "image/png";

    @Autowired
    private ResumeCreationRepository resumeCreationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResumeCreationMockMvc;

    private ResumeCreation resumeCreation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResumeCreationResource resumeCreationResource = new ResumeCreationResource(resumeCreationRepository);
        this.restResumeCreationMockMvc = MockMvcBuilders.standaloneSetup(resumeCreationResource)
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
    public static ResumeCreation createEntity(EntityManager em) {
        ResumeCreation resumeCreation = new ResumeCreation()
            .name(DEFAULT_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .birthdate(DEFAULT_BIRTHDATE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .address(DEFAULT_ADDRESS)
            .skills(DEFAULT_SKILLS)
            .skillsContentType(DEFAULT_SKILLS_CONTENT_TYPE);
        return resumeCreation;
    }

    @Before
    public void initTest() {
        resumeCreation = createEntity(em);
    }

    @Test
    @Transactional
    public void createResumeCreation() throws Exception {
        int databaseSizeBeforeCreate = resumeCreationRepository.findAll().size();

        // Create the ResumeCreation
        restResumeCreationMockMvc.perform(post("/api/resume-creations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumeCreation)))
            .andExpect(status().isCreated());

        // Validate the ResumeCreation in the database
        List<ResumeCreation> resumeCreationList = resumeCreationRepository.findAll();
        assertThat(resumeCreationList).hasSize(databaseSizeBeforeCreate + 1);
        ResumeCreation testResumeCreation = resumeCreationList.get(resumeCreationList.size() - 1);
        assertThat(testResumeCreation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testResumeCreation.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testResumeCreation.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testResumeCreation.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testResumeCreation.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testResumeCreation.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testResumeCreation.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testResumeCreation.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testResumeCreation.getSkills()).isEqualTo(DEFAULT_SKILLS);
        assertThat(testResumeCreation.getSkillsContentType()).isEqualTo(DEFAULT_SKILLS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createResumeCreationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resumeCreationRepository.findAll().size();

        // Create the ResumeCreation with an existing ID
        resumeCreation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResumeCreationMockMvc.perform(post("/api/resume-creations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumeCreation)))
            .andExpect(status().isBadRequest());

        // Validate the ResumeCreation in the database
        List<ResumeCreation> resumeCreationList = resumeCreationRepository.findAll();
        assertThat(resumeCreationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllResumeCreations() throws Exception {
        // Initialize the database
        resumeCreationRepository.saveAndFlush(resumeCreation);

        // Get all the resumeCreationList
        restResumeCreationMockMvc.perform(get("/api/resume-creations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resumeCreation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].skillsContentType").value(hasItem(DEFAULT_SKILLS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].skills").value(hasItem(Base64Utils.encodeToString(DEFAULT_SKILLS))));
    }

    @Test
    @Transactional
    public void getResumeCreation() throws Exception {
        // Initialize the database
        resumeCreationRepository.saveAndFlush(resumeCreation);

        // Get the resumeCreation
        restResumeCreationMockMvc.perform(get("/api/resume-creations/{id}", resumeCreation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resumeCreation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.skillsContentType").value(DEFAULT_SKILLS_CONTENT_TYPE))
            .andExpect(jsonPath("$.skills").value(Base64Utils.encodeToString(DEFAULT_SKILLS)));
    }

    @Test
    @Transactional
    public void getNonExistingResumeCreation() throws Exception {
        // Get the resumeCreation
        restResumeCreationMockMvc.perform(get("/api/resume-creations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResumeCreation() throws Exception {
        // Initialize the database
        resumeCreationRepository.saveAndFlush(resumeCreation);
        int databaseSizeBeforeUpdate = resumeCreationRepository.findAll().size();

        // Update the resumeCreation
        ResumeCreation updatedResumeCreation = resumeCreationRepository.findOne(resumeCreation.getId());
        updatedResumeCreation
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .birthdate(UPDATED_BIRTHDATE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS)
            .skills(UPDATED_SKILLS)
            .skillsContentType(UPDATED_SKILLS_CONTENT_TYPE);

        restResumeCreationMockMvc.perform(put("/api/resume-creations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResumeCreation)))
            .andExpect(status().isOk());

        // Validate the ResumeCreation in the database
        List<ResumeCreation> resumeCreationList = resumeCreationRepository.findAll();
        assertThat(resumeCreationList).hasSize(databaseSizeBeforeUpdate);
        ResumeCreation testResumeCreation = resumeCreationList.get(resumeCreationList.size() - 1);
        assertThat(testResumeCreation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResumeCreation.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testResumeCreation.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testResumeCreation.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testResumeCreation.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testResumeCreation.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testResumeCreation.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testResumeCreation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testResumeCreation.getSkills()).isEqualTo(UPDATED_SKILLS);
        assertThat(testResumeCreation.getSkillsContentType()).isEqualTo(UPDATED_SKILLS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingResumeCreation() throws Exception {
        int databaseSizeBeforeUpdate = resumeCreationRepository.findAll().size();

        // Create the ResumeCreation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restResumeCreationMockMvc.perform(put("/api/resume-creations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumeCreation)))
            .andExpect(status().isCreated());

        // Validate the ResumeCreation in the database
        List<ResumeCreation> resumeCreationList = resumeCreationRepository.findAll();
        assertThat(resumeCreationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteResumeCreation() throws Exception {
        // Initialize the database
        resumeCreationRepository.saveAndFlush(resumeCreation);
        int databaseSizeBeforeDelete = resumeCreationRepository.findAll().size();

        // Get the resumeCreation
        restResumeCreationMockMvc.perform(delete("/api/resume-creations/{id}", resumeCreation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ResumeCreation> resumeCreationList = resumeCreationRepository.findAll();
        assertThat(resumeCreationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumeCreation.class);
        ResumeCreation resumeCreation1 = new ResumeCreation();
        resumeCreation1.setId(1L);
        ResumeCreation resumeCreation2 = new ResumeCreation();
        resumeCreation2.setId(resumeCreation1.getId());
        assertThat(resumeCreation1).isEqualTo(resumeCreation2);
        resumeCreation2.setId(2L);
        assertThat(resumeCreation1).isNotEqualTo(resumeCreation2);
        resumeCreation1.setId(null);
        assertThat(resumeCreation1).isNotEqualTo(resumeCreation2);
    }
}
