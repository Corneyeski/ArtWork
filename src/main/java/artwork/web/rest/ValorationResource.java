package artwork.web.rest;

import artwork.domain.Multimedia;
import artwork.domain.User;
import artwork.repository.MultimediaRepository;
import artwork.repository.UserRepository;
import artwork.security.SecurityUtils;
import com.codahale.metrics.annotation.Timed;
import artwork.domain.Valoration;

import artwork.repository.ValorationRepository;
import artwork.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Valoration.
 */
@RestController
@RequestMapping("/api")
public class ValorationResource {

    private final Logger log = LoggerFactory.getLogger(ValorationResource.class);

    private static final String ENTITY_NAME = "valoration";

    private final ValorationRepository valorationRepository;
    private final MultimediaRepository multimediaRepository;
    private final UserRepository userRepository;

    public ValorationResource(ValorationRepository valorationRepository,
                              MultimediaRepository multimediaRepository,
                              UserRepository userRepository) {
        this.valorationRepository = valorationRepository;
        this.multimediaRepository = multimediaRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /valorations : Create a new valoration.
     *
     * @param valoration the valoration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valoration, or with status 400 (Bad Request) if the valoration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/valorations")
    @Timed
    public ResponseEntity<Valoration> createValoration(@Valid @RequestBody Valoration valoration) throws URISyntaxException {
        log.debug("REST request to save Valoration : {}", valoration);
        if (valoration.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new valoration cannot already have an ID")).body(null);
        }
        Valoration result = valorationRepository.save(valoration);
        return ResponseEntity.created(new URI("/api/valorations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /valorations : Updates an existing valoration.
     *
     * @param valoration the valoration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valoration,
     * or with status 400 (Bad Request) if the valoration is not valid,
     * or with status 500 (Internal Server Error) if the valoration couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/valorations")
    @Timed
    public ResponseEntity<Valoration> updateValoration(@Valid @RequestBody Valoration valoration) throws URISyntaxException {
        log.debug("REST request to update Valoration : {}", valoration);
        if (valoration.getId() == null) {
            return createValoration(valoration);
        }
        Valoration result = valorationRepository.save(valoration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, valoration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /valorations : get all the valorations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of valorations in body
     */
    @GetMapping("/valorations")
    @Timed
    public List<Valoration> getAllValorations() {
        log.debug("REST request to get all Valorations");
        return valorationRepository.findAll();
        }

    /**
     * GET  /valorations/:id : get the "id" valoration.
     *
     * @param id the id of the valoration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valoration, or with status 404 (Not Found)
     */
    @GetMapping("/valorations/{id}")
    @Timed
    public ResponseEntity<Valoration> getValoration(@PathVariable Long id) {
        log.debug("REST request to get Valoration : {}", id);
        Valoration valoration = valorationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(valoration));
    }

    /**
     * DELETE  /valorations/:id : delete the "id" valoration.
     *
     * @param id the id of the valoration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/valorations/{id}")
    @Timed
    public ResponseEntity<Void> deleteValoration(@PathVariable Long id) {
        log.debug("REST request to delete Valoration : {}", id);
        valorationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/setUpdateValorationPhoto")
    @Timed
    public ResponseEntity<Void> setUpdateValoration(@RequestParam Long voted,
                                                    @RequestParam Double value) throws URISyntaxException {

        if (SecurityUtils.getCurrentUserLogin() == null)
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "badLogin", "Necesitas estar logueado para usar esto")).body(null);

        if (value < 0 || value > 5) {
            return ResponseEntity.badRequest().headers(HeaderUtil.
                createEntityDeletionAlert(ENTITY_NAME, "bad score")).build();
        } else {

            User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();

            Multimedia multimedia = multimediaRepository.findOne(voted);

            Valoration valoration = valorationRepository.findByUserAndMultimedia(user, multimedia);

            if (valoration != null) {
                valoration.setMark(value);
                valorationRepository.save(valoration);
            } else {
                valoration = new Valoration();
                valoration.setUser(user);
                valoration.setMultimedia(multimedia);
                valoration.setMark(value);

                valorationRepository.save(valoration);
            }
            Double points = valorationRepository.avgMultimedia(multimedia);
            multimedia.setTotalValoration(points);
            multimediaRepository.save(multimedia);

            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "updated")).build();
        }
    }
}
