package artwork.web.rest;

import artwork.ArtWorkApp;

import artwork.domain.ReasonReport;
import artwork.repository.ReasonReportRepository;
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
 * Test class for the ReasonReportResource REST controller.
 *
 * @see ReasonReportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtWorkApp.class)
public class ReasonReportResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    @Autowired
    private ReasonReportRepository reasonReportRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReasonReportMockMvc;

    private ReasonReport reasonReport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReasonReportResource reasonReportResource = new ReasonReportResource(reasonReportRepository);
        this.restReasonReportMockMvc = MockMvcBuilders.standaloneSetup(reasonReportResource)
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
    public static ReasonReport createEntity(EntityManager em) {
        ReasonReport reasonReport = new ReasonReport()
            .title(DEFAULT_TITLE)
            .reason(DEFAULT_REASON);
        return reasonReport;
    }

    @Before
    public void initTest() {
        reasonReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createReasonReport() throws Exception {
        int databaseSizeBeforeCreate = reasonReportRepository.findAll().size();

        // Create the ReasonReport
        restReasonReportMockMvc.perform(post("/api/reason-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasonReport)))
            .andExpect(status().isCreated());

        // Validate the ReasonReport in the database
        List<ReasonReport> reasonReportList = reasonReportRepository.findAll();
        assertThat(reasonReportList).hasSize(databaseSizeBeforeCreate + 1);
        ReasonReport testReasonReport = reasonReportList.get(reasonReportList.size() - 1);
        assertThat(testReasonReport.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testReasonReport.getReason()).isEqualTo(DEFAULT_REASON);
    }

    @Test
    @Transactional
    public void createReasonReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reasonReportRepository.findAll().size();

        // Create the ReasonReport with an existing ID
        reasonReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReasonReportMockMvc.perform(post("/api/reason-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasonReport)))
            .andExpect(status().isBadRequest());

        // Validate the ReasonReport in the database
        List<ReasonReport> reasonReportList = reasonReportRepository.findAll();
        assertThat(reasonReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReasonReports() throws Exception {
        // Initialize the database
        reasonReportRepository.saveAndFlush(reasonReport);

        // Get all the reasonReportList
        restReasonReportMockMvc.perform(get("/api/reason-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reasonReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())));
    }

    @Test
    @Transactional
    public void getReasonReport() throws Exception {
        // Initialize the database
        reasonReportRepository.saveAndFlush(reasonReport);

        // Get the reasonReport
        restReasonReportMockMvc.perform(get("/api/reason-reports/{id}", reasonReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reasonReport.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReasonReport() throws Exception {
        // Get the reasonReport
        restReasonReportMockMvc.perform(get("/api/reason-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReasonReport() throws Exception {
        // Initialize the database
        reasonReportRepository.saveAndFlush(reasonReport);
        int databaseSizeBeforeUpdate = reasonReportRepository.findAll().size();

        // Update the reasonReport
        ReasonReport updatedReasonReport = reasonReportRepository.findOne(reasonReport.getId());
        updatedReasonReport
            .title(UPDATED_TITLE)
            .reason(UPDATED_REASON);

        restReasonReportMockMvc.perform(put("/api/reason-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReasonReport)))
            .andExpect(status().isOk());

        // Validate the ReasonReport in the database
        List<ReasonReport> reasonReportList = reasonReportRepository.findAll();
        assertThat(reasonReportList).hasSize(databaseSizeBeforeUpdate);
        ReasonReport testReasonReport = reasonReportList.get(reasonReportList.size() - 1);
        assertThat(testReasonReport.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testReasonReport.getReason()).isEqualTo(UPDATED_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingReasonReport() throws Exception {
        int databaseSizeBeforeUpdate = reasonReportRepository.findAll().size();

        // Create the ReasonReport

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReasonReportMockMvc.perform(put("/api/reason-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasonReport)))
            .andExpect(status().isCreated());

        // Validate the ReasonReport in the database
        List<ReasonReport> reasonReportList = reasonReportRepository.findAll();
        assertThat(reasonReportList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReasonReport() throws Exception {
        // Initialize the database
        reasonReportRepository.saveAndFlush(reasonReport);
        int databaseSizeBeforeDelete = reasonReportRepository.findAll().size();

        // Get the reasonReport
        restReasonReportMockMvc.perform(delete("/api/reason-reports/{id}", reasonReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReasonReport> reasonReportList = reasonReportRepository.findAll();
        assertThat(reasonReportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReasonReport.class);
        ReasonReport reasonReport1 = new ReasonReport();
        reasonReport1.setId(1L);
        ReasonReport reasonReport2 = new ReasonReport();
        reasonReport2.setId(reasonReport1.getId());
        assertThat(reasonReport1).isEqualTo(reasonReport2);
        reasonReport2.setId(2L);
        assertThat(reasonReport1).isNotEqualTo(reasonReport2);
        reasonReport1.setId(null);
        assertThat(reasonReport1).isNotEqualTo(reasonReport2);
    }
}
