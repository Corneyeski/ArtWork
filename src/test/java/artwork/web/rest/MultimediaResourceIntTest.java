package artwork.web.rest;

import artwork.ArtWorkApp;

import artwork.domain.Multimedia;
import artwork.repository.MultimediaRepository;
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

import artwork.domain.enumeration.Type;
/**
 * Test class for the MultimediaResource REST controller.
 *
 * @see MultimediaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtWorkApp.class)
public class MultimediaResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_URL_SHARE = "AAAAAAAAAA";
    private static final String UPDATED_URL_SHARE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_VALORATION = 0D;
    private static final Double UPDATED_TOTAL_VALORATION = 1D;

    private static final Type DEFAULT_TYPE = Type.PHOTO;
    private static final Type UPDATED_TYPE = Type.VIDEO;

    private static final byte[] DEFAULT_DESCRIPTION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DESCRIPTION = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_DESCRIPTION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DESCRIPTION_CONTENT_TYPE = "image/png";

    @Autowired
    private MultimediaRepository multimediaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMultimediaMockMvc;

    private Multimedia multimedia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MultimediaResource multimediaResource = new MultimediaResource(multimediaRepository);
        this.restMultimediaMockMvc = MockMvcBuilders.standaloneSetup(multimediaResource)
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
    public static Multimedia createEntity(EntityManager em) {
        Multimedia multimedia = new Multimedia()
            .title(DEFAULT_TITLE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .url(DEFAULT_URL)
            .urlShare(DEFAULT_URL_SHARE)
            .time(DEFAULT_TIME)
            .tags(DEFAULT_TAGS)
            .totalValoration(DEFAULT_TOTAL_VALORATION)
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .descriptionContentType(DEFAULT_DESCRIPTION_CONTENT_TYPE);
        return multimedia;
    }

    @Before
    public void initTest() {
        multimedia = createEntity(em);
    }

    @Test
    @Transactional
    public void createMultimedia() throws Exception {
        int databaseSizeBeforeCreate = multimediaRepository.findAll().size();

        // Create the Multimedia
        restMultimediaMockMvc.perform(post("/api/multimedias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multimedia)))
            .andExpect(status().isCreated());

        // Validate the Multimedia in the database
        List<Multimedia> multimediaList = multimediaRepository.findAll();
        assertThat(multimediaList).hasSize(databaseSizeBeforeCreate + 1);
        Multimedia testMultimedia = multimediaList.get(multimediaList.size() - 1);
        assertThat(testMultimedia.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMultimedia.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testMultimedia.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testMultimedia.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testMultimedia.getUrlShare()).isEqualTo(DEFAULT_URL_SHARE);
        assertThat(testMultimedia.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testMultimedia.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testMultimedia.getTotalValoration()).isEqualTo(DEFAULT_TOTAL_VALORATION);
        assertThat(testMultimedia.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMultimedia.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMultimedia.getDescriptionContentType()).isEqualTo(DEFAULT_DESCRIPTION_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createMultimediaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = multimediaRepository.findAll().size();

        // Create the Multimedia with an existing ID
        multimedia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMultimediaMockMvc.perform(post("/api/multimedias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multimedia)))
            .andExpect(status().isBadRequest());

        // Validate the Multimedia in the database
        List<Multimedia> multimediaList = multimediaRepository.findAll();
        assertThat(multimediaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTotalValorationIsRequired() throws Exception {
        int databaseSizeBeforeTest = multimediaRepository.findAll().size();
        // set the field null
        multimedia.setTotalValoration(null);

        // Create the Multimedia, which fails.

        restMultimediaMockMvc.perform(post("/api/multimedias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multimedia)))
            .andExpect(status().isBadRequest());

        List<Multimedia> multimediaList = multimediaRepository.findAll();
        assertThat(multimediaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMultimedias() throws Exception {
        // Initialize the database
        multimediaRepository.saveAndFlush(multimedia);

        // Get all the multimediaList
        restMultimediaMockMvc.perform(get("/api/multimedias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(multimedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].urlShare").value(hasItem(DEFAULT_URL_SHARE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS.toString())))
            .andExpect(jsonPath("$.[*].totalValoration").value(hasItem(DEFAULT_TOTAL_VALORATION.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].descriptionContentType").value(hasItem(DEFAULT_DESCRIPTION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(Base64Utils.encodeToString(DEFAULT_DESCRIPTION))));
    }

    @Test
    @Transactional
    public void getMultimedia() throws Exception {
        // Initialize the database
        multimediaRepository.saveAndFlush(multimedia);

        // Get the multimedia
        restMultimediaMockMvc.perform(get("/api/multimedias/{id}", multimedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(multimedia.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.urlShare").value(DEFAULT_URL_SHARE.toString()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS.toString()))
            .andExpect(jsonPath("$.totalValoration").value(DEFAULT_TOTAL_VALORATION.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.descriptionContentType").value(DEFAULT_DESCRIPTION_CONTENT_TYPE))
            .andExpect(jsonPath("$.description").value(Base64Utils.encodeToString(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void getNonExistingMultimedia() throws Exception {
        // Get the multimedia
        restMultimediaMockMvc.perform(get("/api/multimedias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMultimedia() throws Exception {
        // Initialize the database
        multimediaRepository.saveAndFlush(multimedia);
        int databaseSizeBeforeUpdate = multimediaRepository.findAll().size();

        // Update the multimedia
        Multimedia updatedMultimedia = multimediaRepository.findOne(multimedia.getId());
        updatedMultimedia
            .title(UPDATED_TITLE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .url(UPDATED_URL)
            .urlShare(UPDATED_URL_SHARE)
            .time(UPDATED_TIME)
            .tags(UPDATED_TAGS)
            .totalValoration(UPDATED_TOTAL_VALORATION)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .descriptionContentType(UPDATED_DESCRIPTION_CONTENT_TYPE);

        restMultimediaMockMvc.perform(put("/api/multimedias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMultimedia)))
            .andExpect(status().isOk());

        // Validate the Multimedia in the database
        List<Multimedia> multimediaList = multimediaRepository.findAll();
        assertThat(multimediaList).hasSize(databaseSizeBeforeUpdate);
        Multimedia testMultimedia = multimediaList.get(multimediaList.size() - 1);
        assertThat(testMultimedia.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMultimedia.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testMultimedia.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testMultimedia.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testMultimedia.getUrlShare()).isEqualTo(UPDATED_URL_SHARE);
        assertThat(testMultimedia.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testMultimedia.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testMultimedia.getTotalValoration()).isEqualTo(UPDATED_TOTAL_VALORATION);
        assertThat(testMultimedia.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMultimedia.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMultimedia.getDescriptionContentType()).isEqualTo(UPDATED_DESCRIPTION_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMultimedia() throws Exception {
        int databaseSizeBeforeUpdate = multimediaRepository.findAll().size();

        // Create the Multimedia

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMultimediaMockMvc.perform(put("/api/multimedias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multimedia)))
            .andExpect(status().isCreated());

        // Validate the Multimedia in the database
        List<Multimedia> multimediaList = multimediaRepository.findAll();
        assertThat(multimediaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMultimedia() throws Exception {
        // Initialize the database
        multimediaRepository.saveAndFlush(multimedia);
        int databaseSizeBeforeDelete = multimediaRepository.findAll().size();

        // Get the multimedia
        restMultimediaMockMvc.perform(delete("/api/multimedias/{id}", multimedia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Multimedia> multimediaList = multimediaRepository.findAll();
        assertThat(multimediaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Multimedia.class);
        Multimedia multimedia1 = new Multimedia();
        multimedia1.setId(1L);
        Multimedia multimedia2 = new Multimedia();
        multimedia2.setId(multimedia1.getId());
        assertThat(multimedia1).isEqualTo(multimedia2);
        multimedia2.setId(2L);
        assertThat(multimedia1).isNotEqualTo(multimedia2);
        multimedia1.setId(null);
        assertThat(multimedia1).isNotEqualTo(multimedia2);
    }
}
