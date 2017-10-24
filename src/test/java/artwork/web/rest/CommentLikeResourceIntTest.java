package artwork.web.rest;

import artwork.ArtWorkApp;

import artwork.domain.CommentLike;
import artwork.repository.CommentLikeRepository;
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
 * Test class for the CommentLikeResource REST controller.
 *
 * @see CommentLikeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtWorkApp.class)
public class CommentLikeResourceIntTest {

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommentLikeMockMvc;

    private CommentLike commentLike;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommentLikeResource commentLikeResource = new CommentLikeResource(commentLikeRepository);
        this.restCommentLikeMockMvc = MockMvcBuilders.standaloneSetup(commentLikeResource)
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
    public static CommentLike createEntity(EntityManager em) {
        CommentLike commentLike = new CommentLike()
            .time(DEFAULT_TIME);
        return commentLike;
    }

    @Before
    public void initTest() {
        commentLike = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommentLike() throws Exception {
        int databaseSizeBeforeCreate = commentLikeRepository.findAll().size();

        // Create the CommentLike
        restCommentLikeMockMvc.perform(post("/api/comment-likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentLike)))
            .andExpect(status().isCreated());

        // Validate the CommentLike in the database
        List<CommentLike> commentLikeList = commentLikeRepository.findAll();
        assertThat(commentLikeList).hasSize(databaseSizeBeforeCreate + 1);
        CommentLike testCommentLike = commentLikeList.get(commentLikeList.size() - 1);
        assertThat(testCommentLike.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createCommentLikeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentLikeRepository.findAll().size();

        // Create the CommentLike with an existing ID
        commentLike.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentLikeMockMvc.perform(post("/api/comment-likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentLike)))
            .andExpect(status().isBadRequest());

        // Validate the CommentLike in the database
        List<CommentLike> commentLikeList = commentLikeRepository.findAll();
        assertThat(commentLikeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommentLikes() throws Exception {
        // Initialize the database
        commentLikeRepository.saveAndFlush(commentLike);

        // Get all the commentLikeList
        restCommentLikeMockMvc.perform(get("/api/comment-likes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentLike.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))));
    }

    @Test
    @Transactional
    public void getCommentLike() throws Exception {
        // Initialize the database
        commentLikeRepository.saveAndFlush(commentLike);

        // Get the commentLike
        restCommentLikeMockMvc.perform(get("/api/comment-likes/{id}", commentLike.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commentLike.getId().intValue()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingCommentLike() throws Exception {
        // Get the commentLike
        restCommentLikeMockMvc.perform(get("/api/comment-likes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommentLike() throws Exception {
        // Initialize the database
        commentLikeRepository.saveAndFlush(commentLike);
        int databaseSizeBeforeUpdate = commentLikeRepository.findAll().size();

        // Update the commentLike
        CommentLike updatedCommentLike = commentLikeRepository.findOne(commentLike.getId());
        updatedCommentLike
            .time(UPDATED_TIME);

        restCommentLikeMockMvc.perform(put("/api/comment-likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommentLike)))
            .andExpect(status().isOk());

        // Validate the CommentLike in the database
        List<CommentLike> commentLikeList = commentLikeRepository.findAll();
        assertThat(commentLikeList).hasSize(databaseSizeBeforeUpdate);
        CommentLike testCommentLike = commentLikeList.get(commentLikeList.size() - 1);
        assertThat(testCommentLike.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingCommentLike() throws Exception {
        int databaseSizeBeforeUpdate = commentLikeRepository.findAll().size();

        // Create the CommentLike

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommentLikeMockMvc.perform(put("/api/comment-likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentLike)))
            .andExpect(status().isCreated());

        // Validate the CommentLike in the database
        List<CommentLike> commentLikeList = commentLikeRepository.findAll();
        assertThat(commentLikeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommentLike() throws Exception {
        // Initialize the database
        commentLikeRepository.saveAndFlush(commentLike);
        int databaseSizeBeforeDelete = commentLikeRepository.findAll().size();

        // Get the commentLike
        restCommentLikeMockMvc.perform(delete("/api/comment-likes/{id}", commentLike.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommentLike> commentLikeList = commentLikeRepository.findAll();
        assertThat(commentLikeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentLike.class);
        CommentLike commentLike1 = new CommentLike();
        commentLike1.setId(1L);
        CommentLike commentLike2 = new CommentLike();
        commentLike2.setId(commentLike1.getId());
        assertThat(commentLike1).isEqualTo(commentLike2);
        commentLike2.setId(2L);
        assertThat(commentLike1).isNotEqualTo(commentLike2);
        commentLike1.setId(null);
        assertThat(commentLike1).isNotEqualTo(commentLike2);
    }
}
