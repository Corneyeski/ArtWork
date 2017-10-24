package artwork.web.rest;

import com.codahale.metrics.annotation.Timed;
import artwork.domain.Training;

import artwork.repository.TrainingRepository;
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
 * REST controller for managing Training.
 */
@RestController
@RequestMapping("/api")
public class TrainingResource {

    private final Logger log = LoggerFactory.getLogger(TrainingResource.class);

    private static final String ENTITY_NAME = "training";

    private final TrainingRepository trainingRepository;

    public TrainingResource(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    /**
     * POST  /trainings : Create a new training.
     *
     * @param training the training to create
     * @return the ResponseEntity with status 201 (Created) and with body the new training, or with status 400 (Bad Request) if the training has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trainings")
    @Timed
    public ResponseEntity<Training> createTraining(@RequestBody Training training) throws URISyntaxException {
        log.debug("REST request to save Training : {}", training);
        if (training.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new training cannot already have an ID")).body(null);
        }
        Training result = trainingRepository.save(training);
        return ResponseEntity.created(new URI("/api/trainings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trainings : Updates an existing training.
     *
     * @param training the training to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated training,
     * or with status 400 (Bad Request) if the training is not valid,
     * or with status 500 (Internal Server Error) if the training couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trainings")
    @Timed
    public ResponseEntity<Training> updateTraining(@RequestBody Training training) throws URISyntaxException {
        log.debug("REST request to update Training : {}", training);
        if (training.getId() == null) {
            return createTraining(training);
        }
        Training result = trainingRepository.save(training);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, training.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trainings : get all the trainings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of trainings in body
     */
    @GetMapping("/trainings")
    @Timed
    public List<Training> getAllTrainings() {
        log.debug("REST request to get all Trainings");
        return trainingRepository.findAll();
        }

    /**
     * GET  /trainings/:id : get the "id" training.
     *
     * @param id the id of the training to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the training, or with status 404 (Not Found)
     */
    @GetMapping("/trainings/{id}")
    @Timed
    public ResponseEntity<Training> getTraining(@PathVariable Long id) {
        log.debug("REST request to get Training : {}", id);
        Training training = trainingRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(training));
    }

    /**
     * DELETE  /trainings/:id : delete the "id" training.
     *
     * @param id the id of the training to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trainings/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        log.debug("REST request to delete Training : {}", id);
        trainingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
