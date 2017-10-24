package artwork.web.rest;

import artwork.ArtWorkApp;

import artwork.domain.UserProfileValoration;
import artwork.repository.UserProfileValorationRepository;
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
 * Test class for the UserProfileValorationResource REST controller.
 *
 * @see UserProfileValorationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtWorkApp.class)
public class UserProfileValorationResourceIntTest {

    private static final Double DEFAULT_VALUE = 0D;
    private static final Double UPDATED_VALUE = 1D;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private UserProfileValorationRepository userProfileValorationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserProfileValorationMockMvc;

    private UserProfileValoration userProfileValoration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserProfileValorationResource userProfileValorationResource = new UserProfileValorationResource(userProfileValorationRepository);
        this.restUserProfileValorationMockMvc = MockMvcBuilders.standaloneSetup(userProfileValorationResource)
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
    public static UserProfileValoration createEntity(EntityManager em) {
        UserProfileValoration userProfileValoration = new UserProfileValoration()
            .value(DEFAULT_VALUE)
            .comments(DEFAULT_COMMENTS);
        return userProfileValoration;
    }

    @Before
    public void initTest() {
        userProfileValoration = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserProfileValoration() throws Exception {
        int databaseSizeBeforeCreate = userProfileValorationRepository.findAll().size();

        // Create the UserProfileValoration
        restUserProfileValorationMockMvc.perform(post("/api/user-profile-valorations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userProfileValoration)))
            .andExpect(status().isCreated());

        // Validate the UserProfileValoration in the database
        List<UserProfileValoration> userProfileValorationList = userProfileValorationRepository.findAll();
        assertThat(userProfileValorationList).hasSize(databaseSizeBeforeCreate + 1);
        UserProfileValoration testUserProfileValoration = userProfileValorationList.get(userProfileValorationList.size() - 1);
        assertThat(testUserProfileValoration.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testUserProfileValoration.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createUserProfileValorationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userProfileValorationRepository.findAll().size();

        // Create the UserProfileValoration with an existing ID
        userProfileValoration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserProfileValorationMockMvc.perform(post("/api/user-profile-valorations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userProfileValoration)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfileValoration in the database
        List<UserProfileValoration> userProfileValorationList = userProfileValorationRepository.findAll();
        assertThat(userProfileValorationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileValorationRepository.findAll().size();
        // set the field null
        userProfileValoration.setValue(null);

        // Create the UserProfileValoration, which fails.

        restUserProfileValorationMockMvc.perform(post("/api/user-profile-valorations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userProfileValoration)))
            .andExpect(status().isBadRequest());

        List<UserProfileValoration> userProfileValorationList = userProfileValorationRepository.findAll();
        assertThat(userProfileValorationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserProfileValorations() throws Exception {
        // Initialize the database
        userProfileValorationRepository.saveAndFlush(userProfileValoration);

        // Get all the userProfileValorationList
        restUserProfileValorationMockMvc.perform(get("/api/user-profile-valorations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProfileValoration.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void getUserProfileValoration() throws Exception {
        // Initialize the database
        userProfileValorationRepository.saveAndFlush(userProfileValoration);

        // Get the userProfileValoration
        restUserProfileValorationMockMvc.perform(get("/api/user-profile-valorations/{id}", userProfileValoration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userProfileValoration.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserProfileValoration() throws Exception {
        // Get the userProfileValoration
        restUserProfileValorationMockMvc.perform(get("/api/user-profile-valorations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserProfileValoration() throws Exception {
        // Initialize the database
        userProfileValorationRepository.saveAndFlush(userProfileValoration);
        int databaseSizeBeforeUpdate = userProfileValorationRepository.findAll().size();

        // Update the userProfileValoration
        UserProfileValoration updatedUserProfileValoration = userProfileValorationRepository.findOne(userProfileValoration.getId());
        updatedUserProfileValoration
            .value(UPDATED_VALUE)
            .comments(UPDATED_COMMENTS);

        restUserProfileValorationMockMvc.perform(put("/api/user-profile-valorations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserProfileValoration)))
            .andExpect(status().isOk());

        // Validate the UserProfileValoration in the database
        List<UserProfileValoration> userProfileValorationList = userProfileValorationRepository.findAll();
        assertThat(userProfileValorationList).hasSize(databaseSizeBeforeUpdate);
        UserProfileValoration testUserProfileValoration = userProfileValorationList.get(userProfileValorationList.size() - 1);
        assertThat(testUserProfileValoration.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testUserProfileValoration.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingUserProfileValoration() throws Exception {
        int databaseSizeBeforeUpdate = userProfileValorationRepository.findAll().size();

        // Create the UserProfileValoration

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserProfileValorationMockMvc.perform(put("/api/user-profile-valorations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userProfileValoration)))
            .andExpect(status().isCreated());

        // Validate the UserProfileValoration in the database
        List<UserProfileValoration> userProfileValorationList = userProfileValorationRepository.findAll();
        assertThat(userProfileValorationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserProfileValoration() throws Exception {
        // Initialize the database
        userProfileValorationRepository.saveAndFlush(userProfileValoration);
        int databaseSizeBeforeDelete = userProfileValorationRepository.findAll().size();

        // Get the userProfileValoration
        restUserProfileValorationMockMvc.perform(delete("/api/user-profile-valorations/{id}", userProfileValoration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserProfileValoration> userProfileValorationList = userProfileValorationRepository.findAll();
        assertThat(userProfileValorationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserProfileValoration.class);
        UserProfileValoration userProfileValoration1 = new UserProfileValoration();
        userProfileValoration1.setId(1L);
        UserProfileValoration userProfileValoration2 = new UserProfileValoration();
        userProfileValoration2.setId(userProfileValoration1.getId());
        assertThat(userProfileValoration1).isEqualTo(userProfileValoration2);
        userProfileValoration2.setId(2L);
        assertThat(userProfileValoration1).isNotEqualTo(userProfileValoration2);
        userProfileValoration1.setId(null);
        assertThat(userProfileValoration1).isNotEqualTo(userProfileValoration2);
    }
}
