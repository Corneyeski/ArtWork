package artwork.web.rest;

import com.codahale.metrics.annotation.Timed;
import artwork.domain.CommentLike;

import artwork.repository.CommentLikeRepository;
import artwork.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CommentLike.
 */
@RestController
@RequestMapping("/api")
public class CommentLikeResource {

    private final Logger log = LoggerFactory.getLogger(CommentLikeResource.class);

    private static final String ENTITY_NAME = "commentLike";

    private final CommentLikeRepository commentLikeRepository;

    public CommentLikeResource(CommentLikeRepository commentLikeRepository) {
        this.commentLikeRepository = commentLikeRepository;
    }

    /**
     * POST  /comment-likes : Create a new commentLike.
     *
     * @param commentLike the commentLike to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commentLike, or with status 400 (Bad Request) if the commentLike has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comment-likes")
    @Timed
    public ResponseEntity<CommentLike> createCommentLike(@RequestBody CommentLike commentLike) throws URISyntaxException {
        log.debug("REST request to save CommentLike : {}", commentLike);
        if (commentLike.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new commentLike cannot already have an ID")).body(null);
        }
        CommentLike result = commentLikeRepository.save(commentLike);
        return ResponseEntity.created(new URI("/api/comment-likes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comment-likes : Updates an existing commentLike.
     *
     * @param commentLike the commentLike to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commentLike,
     * or with status 400 (Bad Request) if the commentLike is not valid,
     * or with status 500 (Internal Server Error) if the commentLike couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comment-likes")
    @Timed
    public ResponseEntity<CommentLike> updateCommentLike(@RequestBody CommentLike commentLike) throws URISyntaxException {
        log.debug("REST request to update CommentLike : {}", commentLike);
        if (commentLike.getId() == null) {
            return createCommentLike(commentLike);
        }
        CommentLike result = commentLikeRepository.save(commentLike);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commentLike.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comment-likes : get all the commentLikes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commentLikes in body
     */
    @GetMapping("/comment-likes")
    @Timed
    public List<CommentLike> getAllCommentLikes() {
        log.debug("REST request to get all CommentLikes");
        return commentLikeRepository.findAll();
        }

    /**
     * GET  /comment-likes/:id : get the "id" commentLike.
     *
     * @param id the id of the commentLike to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commentLike, or with status 404 (Not Found)
     */
    @GetMapping("/comment-likes/{id}")
    @Timed
    public ResponseEntity<CommentLike> getCommentLike(@PathVariable Long id) {
        log.debug("REST request to get CommentLike : {}", id);
        CommentLike commentLike = commentLikeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commentLike));
    }

    /**
     * DELETE  /comment-likes/:id : delete the "id" commentLike.
     *
     * @param id the id of the commentLike to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comment-likes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommentLike(@PathVariable Long id) {
        log.debug("REST request to delete CommentLike : {}", id);
        commentLikeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
