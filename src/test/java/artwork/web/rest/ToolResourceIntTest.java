package artwork.web.rest;

import artwork.ArtWorkApp;

import artwork.domain.Tool;
import artwork.repository.ToolRepository;
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

import artwork.domain.enumeration.ToolType;
/**
 * Test class for the ToolResource REST controller.
 *
 * @see ToolResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtWorkApp.class)
public class ToolResourceIntTest {

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ToolType DEFAULT_TYPE = ToolType.BUTTON;
    private static final ToolType UPDATED_TYPE = ToolType.MODAL;

    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restToolMockMvc;

    private Tool tool;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ToolResource toolResource = new ToolResource(toolRepository);
        this.restToolMockMvc = MockMvcBuilders.standaloneSetup(toolResource)
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
    public static Tool createEntity(EntityManager em) {
        Tool tool = new Tool()
            .active(DEFAULT_ACTIVE)
            .time(DEFAULT_TIME)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE);
        return tool;
    }

    @Before
    public void initTest() {
        tool = createEntity(em);
    }

    @Test
    @Transactional
    public void createTool() throws Exception {
        int databaseSizeBeforeCreate = toolRepository.findAll().size();

        // Create the Tool
        restToolMockMvc.perform(post("/api/tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tool)))
            .andExpect(status().isCreated());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeCreate + 1);
        Tool testTool = toolList.get(toolList.size() - 1);
        assertThat(testTool.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testTool.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testTool.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTool.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTool.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createToolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = toolRepository.findAll().size();

        // Create the Tool with an existing ID
        tool.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restToolMockMvc.perform(post("/api/tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tool)))
            .andExpect(status().isBadRequest());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTools() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        // Get all the toolList
        restToolMockMvc.perform(get("/api/tools?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tool.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getTool() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);

        // Get the tool
        restToolMockMvc.perform(get("/api/tools/{id}", tool.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tool.getId().intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTool() throws Exception {
        // Get the tool
        restToolMockMvc.perform(get("/api/tools/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTool() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);
        int databaseSizeBeforeUpdate = toolRepository.findAll().size();

        // Update the tool
        Tool updatedTool = toolRepository.findOne(tool.getId());
        updatedTool
            .active(UPDATED_ACTIVE)
            .time(UPDATED_TIME)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE);

        restToolMockMvc.perform(put("/api/tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTool)))
            .andExpect(status().isOk());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate);
        Tool testTool = toolList.get(toolList.size() - 1);
        assertThat(testTool.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testTool.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testTool.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTool.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTool.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTool() throws Exception {
        int databaseSizeBeforeUpdate = toolRepository.findAll().size();

        // Create the Tool

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restToolMockMvc.perform(put("/api/tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tool)))
            .andExpect(status().isCreated());

        // Validate the Tool in the database
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTool() throws Exception {
        // Initialize the database
        toolRepository.saveAndFlush(tool);
        int databaseSizeBeforeDelete = toolRepository.findAll().size();

        // Get the tool
        restToolMockMvc.perform(delete("/api/tools/{id}", tool.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tool> toolList = toolRepository.findAll();
        assertThat(toolList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tool.class);
        Tool tool1 = new Tool();
        tool1.setId(1L);
        Tool tool2 = new Tool();
        tool2.setId(tool1.getId());
        assertThat(tool1).isEqualTo(tool2);
        tool2.setId(2L);
        assertThat(tool1).isNotEqualTo(tool2);
        tool1.setId(null);
        assertThat(tool1).isNotEqualTo(tool2);
    }
}
