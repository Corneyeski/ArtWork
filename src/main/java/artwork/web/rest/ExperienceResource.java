package artwork.web.rest;

import com.codahale.metrics.annotation.Timed;
import artwork.domain.Experience;

import artwork.repository.ExperienceRepository;
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
 * REST controller for managing Experience.
 */
@RestController
@RequestMapping("/api")
public class ExperienceResource {

    private final Logger log = LoggerFactory.getLogger(ExperienceResource.class);

    private static final String ENTITY_NAME = "experience";

    private final ExperienceRepository experienceRepository;

    public ExperienceResource(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    /**
     * POST  /experiences : Create a new experience.
     *
     * @param experience the experience to create
     * @return the ResponseEntity with status 201 (Created) and with body the new experience, or with status 400 (Bad Request) if the experience has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/experiences")
    @Timed
    public ResponseEntity<Experience> createExperience(@RequestBody Experience experience) throws URISyntaxException {
        log.debug("REST request to save Experience : {}", experience);
        if (experience.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new experience cannot already have an ID")).body(null);
        }
        Experience result = experienceRepository.save(experience);
        return ResponseEntity.created(new URI("/api/experiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /experiences : Updates an existing experience.
     *
     * @param experience the experience to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated experience,
     * or with status 400 (Bad Request) if the experience is not valid,
     * or with status 500 (Internal Server Error) if the experience couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/experiences")
    @Timed
    public ResponseEntity<Experience> updateExperience(@RequestBody Experience experience) throws URISyntaxException {
        log.debug("REST request to update Experience : {}", experience);
        if (experience.getId() == null) {
            return createExperience(experience);
        }
        Experience result = experienceRepository.save(experience);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, experience.getId().toString()))
            .body(result);
    }

    /**
     * GET  /experiences : get all the experiences.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of experiences in body
     */
    @GetMapping("/experiences")
    @Timed
    public List<Experience> getAllExperiences() {
        log.debug("REST request to get all Experiences");
        return experienceRepository.findAll();
        }

    /**
     * GET  /experiences/:id : get the "id" experience.
     *
     * @param id the id of the experience to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the experience, or with status 404 (Not Found)
     */
    @GetMapping("/experiences/{id}")
    @Timed
    public ResponseEntity<Experience> getExperience(@PathVariable Long id) {
        log.debug("REST request to get Experience : {}", id);
        Experience experience = experienceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(experience));
    }

    /**
     * DELETE  /experiences/:id : delete the "id" experience.
     *
     * @param id the id of the experience to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/experiences/{id}")
    @Timed
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        log.debug("REST request to delete Experience : {}", id);
        experienceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
