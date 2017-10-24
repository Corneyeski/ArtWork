package artwork.web.rest;

import artwork.ArtWorkApp;

import artwork.domain.UserExt;
import artwork.repository.UserExtRepository;
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

import artwork.domain.enumeration.Theme;
/**
 * Test class for the UserExtResource REST controller.
 *
 * @see UserExtResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtWorkApp.class)
public class UserExtResourceIntTest {

    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_PHONE = 1;
    private static final Integer UPDATED_PHONE = 2;

    private static final Integer DEFAULT_KIND = 1;
    private static final Integer UPDATED_KIND = 2;

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_POPULAR = 0D;
    private static final Double UPDATED_POPULAR = 1D;

    private static final Double DEFAULT_COMPANY_POINTS = 0D;
    private static final Double UPDATED_COMPANY_POINTS = 1D;

    private static final Boolean DEFAULT_VALIDATED = false;
    private static final Boolean UPDATED_VALIDATED = true;

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final Boolean DEFAULT_WORKING = false;
    private static final Boolean UPDATED_WORKING = true;

    private static final Theme DEFAULT_THEME = Theme.BLACK;
    private static final Theme UPDATED_THEME = Theme.WHITE;

    private static final byte[] DEFAULT_RESUME = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RESUME = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_RESUME_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RESUME_CONTENT_TYPE = "image/png";

    @Autowired
    private UserExtRepository userExtRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserExtMockMvc;

    private UserExt userExt;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserExtResource userExtResource = new UserExtResource(userExtRepository);
        this.restUserExtMockMvc = MockMvcBuilders.standaloneSetup(userExtResource)
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
    public static UserExt createEntity(EntityManager em) {
        UserExt userExt = new UserExt()
            .birthdate(DEFAULT_BIRTHDATE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .phone(DEFAULT_PHONE)
            .kind(DEFAULT_KIND)
            .tags(DEFAULT_TAGS)
            .address(DEFAULT_ADDRESS)
            .popular(DEFAULT_POPULAR)
            .companyPoints(DEFAULT_COMPANY_POINTS)
            .validated(DEFAULT_VALIDATED)
            .age(DEFAULT_AGE)
            .working(DEFAULT_WORKING)
            .theme(DEFAULT_THEME)
            .resume(DEFAULT_RESUME)
            .resumeContentType(DEFAULT_RESUME_CONTENT_TYPE);
        return userExt;
    }

    @Before
    public void initTest() {
        userExt = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserExt() throws Exception {
        int databaseSizeBeforeCreate = userExtRepository.findAll().size();

        // Create the UserExt
        restUserExtMockMvc.perform(post("/api/user-exts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExt)))
            .andExpect(status().isCreated());

        // Validate the UserExt in the database
        List<UserExt> userExtList = userExtRepository.findAll();
        assertThat(userExtList).hasSize(databaseSizeBeforeCreate + 1);
        UserExt testUserExt = userExtList.get(userExtList.size() - 1);
        assertThat(testUserExt.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testUserExt.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testUserExt.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testUserExt.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testUserExt.getKind()).isEqualTo(DEFAULT_KIND);
        assertThat(testUserExt.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testUserExt.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testUserExt.getPopular()).isEqualTo(DEFAULT_POPULAR);
        assertThat(testUserExt.getCompanyPoints()).isEqualTo(DEFAULT_COMPANY_POINTS);
        assertThat(testUserExt.isValidated()).isEqualTo(DEFAULT_VALIDATED);
        assertThat(testUserExt.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testUserExt.isWorking()).isEqualTo(DEFAULT_WORKING);
        assertThat(testUserExt.getTheme()).isEqualTo(DEFAULT_THEME);
        assertThat(testUserExt.getResume()).isEqualTo(DEFAULT_RESUME);
        assertThat(testUserExt.getResumeContentType()).isEqualTo(DEFAULT_RESUME_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createUserExtWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userExtRepository.findAll().size();

        // Create the UserExt with an existing ID
        userExt.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtMockMvc.perform(post("/api/user-exts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExt)))
            .andExpect(status().isBadRequest());

        // Validate the UserExt in the database
        List<UserExt> userExtList = userExtRepository.findAll();
        assertThat(userExtList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserExts() throws Exception {
        // Initialize the database
        userExtRepository.saveAndFlush(userExt);

        // Get all the userExtList
        restUserExtMockMvc.perform(get("/api/user-exts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExt.getId().intValue())))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].kind").value(hasItem(DEFAULT_KIND)))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].popular").value(hasItem(DEFAULT_POPULAR.doubleValue())))
            .andExpect(jsonPath("$.[*].companyPoints").value(hasItem(DEFAULT_COMPANY_POINTS.doubleValue())))
            .andExpect(jsonPath("$.[*].validated").value(hasItem(DEFAULT_VALIDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].working").value(hasItem(DEFAULT_WORKING.booleanValue())))
            .andExpect(jsonPath("$.[*].theme").value(hasItem(DEFAULT_THEME.toString())))
            .andExpect(jsonPath("$.[*].resumeContentType").value(hasItem(DEFAULT_RESUME_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resume").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESUME))));
    }

    @Test
    @Transactional
    public void getUserExt() throws Exception {
        // Initialize the database
        userExtRepository.saveAndFlush(userExt);

        // Get the userExt
        restUserExtMockMvc.perform(get("/api/user-exts/{id}", userExt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userExt.getId().intValue()))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.kind").value(DEFAULT_KIND))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.popular").value(DEFAULT_POPULAR.doubleValue()))
            .andExpect(jsonPath("$.companyPoints").value(DEFAULT_COMPANY_POINTS.doubleValue()))
            .andExpect(jsonPath("$.validated").value(DEFAULT_VALIDATED.booleanValue()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.working").value(DEFAULT_WORKING.booleanValue()))
            .andExpect(jsonPath("$.theme").value(DEFAULT_THEME.toString()))
            .andExpect(jsonPath("$.resumeContentType").value(DEFAULT_RESUME_CONTENT_TYPE))
            .andExpect(jsonPath("$.resume").value(Base64Utils.encodeToString(DEFAULT_RESUME)));
    }

    @Test
    @Transactional
    public void getNonExistingUserExt() throws Exception {
        // Get the userExt
        restUserExtMockMvc.perform(get("/api/user-exts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserExt() throws Exception {
        // Initialize the database
        userExtRepository.saveAndFlush(userExt);
        int databaseSizeBeforeUpdate = userExtRepository.findAll().size();

        // Update the userExt
        UserExt updatedUserExt = userExtRepository.findOne(userExt.getId());
        updatedUserExt
            .birthdate(UPDATED_BIRTHDATE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .phone(UPDATED_PHONE)
            .kind(UPDATED_KIND)
            .tags(UPDATED_TAGS)
            .address(UPDATED_ADDRESS)
            .popular(UPDATED_POPULAR)
            .companyPoints(UPDATED_COMPANY_POINTS)
            .validated(UPDATED_VALIDATED)
            .age(UPDATED_AGE)
            .working(UPDATED_WORKING)
            .theme(UPDATED_THEME)
            .resume(UPDATED_RESUME)
            .resumeContentType(UPDATED_RESUME_CONTENT_TYPE);

        restUserExtMockMvc.perform(put("/api/user-exts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserExt)))
            .andExpect(status().isOk());

        // Validate the UserExt in the database
        List<UserExt> userExtList = userExtRepository.findAll();
        assertThat(userExtList).hasSize(databaseSizeBeforeUpdate);
        UserExt testUserExt = userExtList.get(userExtList.size() - 1);
        assertThat(testUserExt.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testUserExt.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testUserExt.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testUserExt.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testUserExt.getKind()).isEqualTo(UPDATED_KIND);
        assertThat(testUserExt.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testUserExt.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testUserExt.getPopular()).isEqualTo(UPDATED_POPULAR);
        assertThat(testUserExt.getCompanyPoints()).isEqualTo(UPDATED_COMPANY_POINTS);
        assertThat(testUserExt.isValidated()).isEqualTo(UPDATED_VALIDATED);
        assertThat(testUserExt.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testUserExt.isWorking()).isEqualTo(UPDATED_WORKING);
        assertThat(testUserExt.getTheme()).isEqualTo(UPDATED_THEME);
        assertThat(testUserExt.getResume()).isEqualTo(UPDATED_RESUME);
        assertThat(testUserExt.getResumeContentType()).isEqualTo(UPDATED_RESUME_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserExt() throws Exception {
        int databaseSizeBeforeUpdate = userExtRepository.findAll().size();

        // Create the UserExt

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserExtMockMvc.perform(put("/api/user-exts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExt)))
            .andExpect(status().isCreated());

        // Validate the UserExt in the database
        List<UserExt> userExtList = userExtRepository.findAll();
        assertThat(userExtList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserExt() throws Exception {
        // Initialize the database
        userExtRepository.saveAndFlush(userExt);
        int databaseSizeBeforeDelete = userExtRepository.findAll().size();

        // Get the userExt
        restUserExtMockMvc.perform(delete("/api/user-exts/{id}", userExt.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserExt> userExtList = userExtRepository.findAll();
        assertThat(userExtList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserExt.class);
        UserExt userExt1 = new UserExt();
        userExt1.setId(1L);
        UserExt userExt2 = new UserExt();
        userExt2.setId(userExt1.getId());
        assertThat(userExt1).isEqualTo(userExt2);
        userExt2.setId(2L);
        assertThat(userExt1).isNotEqualTo(userExt2);
        userExt1.setId(null);
        assertThat(userExt1).isNotEqualTo(userExt2);
    }
}
