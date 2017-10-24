package artwork.web.rest;

import com.codahale.metrics.annotation.Timed;
import artwork.domain.Blocked;

import artwork.repository.BlockedRepository;
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
 * REST controller for managing Blocked.
 */
@RestController
@RequestMapping("/api")
public class BlockedResource {

    private final Logger log = LoggerFactory.getLogger(BlockedResource.class);

    private static final String ENTITY_NAME = "blocked";

    private final BlockedRepository blockedRepository;

    public BlockedResource(BlockedRepository blockedRepository) {
        this.blockedRepository = blockedRepository;
    }

    /**
     * POST  /blockeds : Create a new blocked.
     *
     * @param blocked the blocked to create
     * @return the ResponseEntity with status 201 (Created) and with body the new blocked, or with status 400 (Bad Request) if the blocked has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/blockeds")
    @Timed
    public ResponseEntity<Blocked> createBlocked(@RequestBody Blocked blocked) throws URISyntaxException {
        log.debug("REST request to save Blocked : {}", blocked);
        if (blocked.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new blocked cannot already have an ID")).body(null);
        }
        Blocked result = blockedRepository.save(blocked);
        return ResponseEntity.created(new URI("/api/blockeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /blockeds : Updates an existing blocked.
     *
     * @param blocked the blocked to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated blocked,
     * or with status 400 (Bad Request) if the blocked is not valid,
     * or with status 500 (Internal Server Error) if the blocked couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/blockeds")
    @Timed
    public ResponseEntity<Blocked> updateBlocked(@RequestBody Blocked blocked) throws URISyntaxException {
        log.debug("REST request to update Blocked : {}", blocked);
        if (blocked.getId() == null) {
            return createBlocked(blocked);
        }
        Blocked result = blockedRepository.save(blocked);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, blocked.getId().toString()))
            .body(result);
    }

    /**
     * GET  /blockeds : get all the blockeds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of blockeds in body
     */
    @GetMapping("/blockeds")
    @Timed
    public List<Blocked> getAllBlockeds() {
        log.debug("REST request to get all Blockeds");
        return blockedRepository.findAll();
        }

    /**
     * GET  /blockeds/:id : get the "id" blocked.
     *
     * @param id the id of the blocked to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the blocked, or with status 404 (Not Found)
     */
    @GetMapping("/blockeds/{id}")
    @Timed
    public ResponseEntity<Blocked> getBlocked(@PathVariable Long id) {
        log.debug("REST request to get Blocked : {}", id);
        Blocked blocked = blockedRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(blocked));
    }

    /**
     * DELETE  /blockeds/:id : delete the "id" blocked.
     *
     * @param id the id of the blocked to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/blockeds/{id}")
    @Timed
    public ResponseEntity<Void> deleteBlocked(@PathVariable Long id) {
        log.debug("REST request to delete Blocked : {}", id);
        blockedRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
