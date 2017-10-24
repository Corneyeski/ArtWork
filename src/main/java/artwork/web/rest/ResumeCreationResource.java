package artwork.web.rest;

import com.codahale.metrics.annotation.Timed;
import artwork.domain.ResumeCreation;

import artwork.repository.ResumeCreationRepository;
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
 * REST controller for managing ResumeCreation.
 */
@RestController
@RequestMapping("/api")
public class ResumeCreationResource {

    private final Logger log = LoggerFactory.getLogger(ResumeCreationResource.class);

    private static final String ENTITY_NAME = "resumeCreation";

    private final ResumeCreationRepository resumeCreationRepository;

    public ResumeCreationResource(ResumeCreationRepository resumeCreationRepository) {
        this.resumeCreationRepository = resumeCreationRepository;
    }

    /**
     * POST  /resume-creations : Create a new resumeCreation.
     *
     * @param resumeCreation the resumeCreation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resumeCreation, or with status 400 (Bad Request) if the resumeCreation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resume-creations")
    @Timed
    public ResponseEntity<ResumeCreation> createResumeCreation(@RequestBody ResumeCreation resumeCreation) throws URISyntaxException {
        log.debug("REST request to save ResumeCreation : {}", resumeCreation);
        if (resumeCreation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new resumeCreation cannot already have an ID")).body(null);
        }
        ResumeCreation result = resumeCreationRepository.save(resumeCreation);
        return ResponseEntity.created(new URI("/api/resume-creations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resume-creations : Updates an existing resumeCreation.
     *
     * @param resumeCreation the resumeCreation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resumeCreation,
     * or with status 400 (Bad Request) if the resumeCreation is not valid,
     * or with status 500 (Internal Server Error) if the resumeCreation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resume-creations")
    @Timed
    public ResponseEntity<ResumeCreation> updateResumeCreation(@RequestBody ResumeCreation resumeCreation) throws URISyntaxException {
        log.debug("REST request to update ResumeCreation : {}", resumeCreation);
        if (resumeCreation.getId() == null) {
            return createResumeCreation(resumeCreation);
        }
        ResumeCreation result = resumeCreationRepository.save(resumeCreation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resumeCreation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resume-creations : get all the resumeCreations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of resumeCreations in body
     */
    @GetMapping("/resume-creations")
    @Timed
    public List<ResumeCreation> getAllResumeCreations() {
        log.debug("REST request to get all ResumeCreations");
        return resumeCreationRepository.findAll();
        }

    /**
     * GET  /resume-creations/:id : get the "id" resumeCreation.
     *
     * @param id the id of the resumeCreation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resumeCreation, or with status 404 (Not Found)
     */
    @GetMapping("/resume-creations/{id}")
    @Timed
    public ResponseEntity<ResumeCreation> getResumeCreation(@PathVariable Long id) {
        log.debug("REST request to get ResumeCreation : {}", id);
        ResumeCreation resumeCreation = resumeCreationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(resumeCreation));
    }

    /**
     * DELETE  /resume-creations/:id : delete the "id" resumeCreation.
     *
     * @param id the id of the resumeCreation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resume-creations/{id}")
    @Timed
    public ResponseEntity<Void> deleteResumeCreation(@PathVariable Long id) {
        log.debug("REST request to delete ResumeCreation : {}", id);
        resumeCreationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
