package artwork.web.rest;

import com.codahale.metrics.annotation.Timed;
import artwork.domain.PricesT;

import artwork.repository.PricesTRepository;
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
 * REST controller for managing PricesT.
 */
@RestController
@RequestMapping("/api")
public class PricesTResource {

    private final Logger log = LoggerFactory.getLogger(PricesTResource.class);

    private static final String ENTITY_NAME = "pricesT";

    private final PricesTRepository pricesTRepository;

    public PricesTResource(PricesTRepository pricesTRepository) {
        this.pricesTRepository = pricesTRepository;
    }

    /**
     * POST  /prices-ts : Create a new pricesT.
     *
     * @param pricesT the pricesT to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pricesT, or with status 400 (Bad Request) if the pricesT has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prices-ts")
    @Timed
    public ResponseEntity<PricesT> createPricesT(@RequestBody PricesT pricesT) throws URISyntaxException {
        log.debug("REST request to save PricesT : {}", pricesT);
        if (pricesT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pricesT cannot already have an ID")).body(null);
        }
        PricesT result = pricesTRepository.save(pricesT);
        return ResponseEntity.created(new URI("/api/prices-ts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prices-ts : Updates an existing pricesT.
     *
     * @param pricesT the pricesT to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pricesT,
     * or with status 400 (Bad Request) if the pricesT is not valid,
     * or with status 500 (Internal Server Error) if the pricesT couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prices-ts")
    @Timed
    public ResponseEntity<PricesT> updatePricesT(@RequestBody PricesT pricesT) throws URISyntaxException {
        log.debug("REST request to update PricesT : {}", pricesT);
        if (pricesT.getId() == null) {
            return createPricesT(pricesT);
        }
        PricesT result = pricesTRepository.save(pricesT);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pricesT.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prices-ts : get all the pricesTS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pricesTS in body
     */
    @GetMapping("/prices-ts")
    @Timed
    public List<PricesT> getAllPricesTS() {
        log.debug("REST request to get all PricesTS");
        return pricesTRepository.findAll();
        }

    /**
     * GET  /prices-ts/:id : get the "id" pricesT.
     *
     * @param id the id of the pricesT to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pricesT, or with status 404 (Not Found)
     */
    @GetMapping("/prices-ts/{id}")
    @Timed
    public ResponseEntity<PricesT> getPricesT(@PathVariable Long id) {
        log.debug("REST request to get PricesT : {}", id);
        PricesT pricesT = pricesTRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pricesT));
    }

    /**
     * DELETE  /prices-ts/:id : delete the "id" pricesT.
     *
     * @param id the id of the pricesT to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prices-ts/{id}")
    @Timed
    public ResponseEntity<Void> deletePricesT(@PathVariable Long id) {
        log.debug("REST request to delete PricesT : {}", id);
        pricesTRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
