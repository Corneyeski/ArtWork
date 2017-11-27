package artwork.web.rest;

import com.codahale.metrics.annotation.Timed;
import artwork.domain.Profession;

import artwork.repository.ProfessionRepository;
import artwork.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing Profession.
 */
@RestController
@RequestMapping("/api")
public class ProfessionResource {

    private final Logger log = LoggerFactory.getLogger(ProfessionResource.class);

    private static final String ENTITY_NAME = "profession";

    private final ProfessionRepository professionRepository;

    public ProfessionResource(ProfessionRepository professionRepository) {
        this.professionRepository = professionRepository;
    }

    /**
     * POST  /professions : Create a new profession.
     *
     * @param profession the profession to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profession, or with status 400 (Bad Request) if the profession has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/professions")
    @Timed
    public ResponseEntity<Profession> createProfession(@RequestBody Profession profession) throws URISyntaxException {
        log.debug("REST request to save Profession : {}", profession);
        if (profession.getId() != null) {
            throw new BadRequestAlertException("A new profession cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Profession result = professionRepository.save(profession);
        return ResponseEntity.created(new URI("/api/professions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /professions : Updates an existing profession.
     *
     * @param profession the profession to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profession,
     * or with status 400 (Bad Request) if the profession is not valid,
     * or with status 500 (Internal Server Error) if the profession couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/professions")
    @Timed
    public ResponseEntity<Profession> updateProfession(@RequestBody Profession profession) throws URISyntaxException {
        log.debug("REST request to update Profession : {}", profession);
        if (profession.getId() == null) {
            return createProfession(profession);
        }
        Profession result = professionRepository.save(profession);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profession.getId().toString()))
            .body(result);
    }

    /**
     * GET  /professions : get all the professions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of professions in body
     */
    @GetMapping("/professions")
    @Timed
    public List<Profession> getAllProfessions() {
        log.debug("REST request to get all Professions");
        return professionRepository.findAll();
        }

    /**
     * GET  /professions/:id : get the "id" profession.
     *
     * @param id the id of the profession to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profession, or with status 404 (Not Found)
     */
    @GetMapping("/professions/{id}")
    @Timed
    public ResponseEntity<Profession> getProfession(@PathVariable Long id) {
        log.debug("REST request to get Profession : {}", id);
        Profession profession = professionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profession));
    }

    /**
     * DELETE  /professions/:id : delete the "id" profession.
     *
     * @param id the id of the profession to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/professions/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfession(@PathVariable Long id) {
        log.debug("REST request to delete Profession : {}", id);
        professionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
