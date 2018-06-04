package artwork.web.rest;

import artwork.domain.User;
import artwork.repository.UserRepository;
import artwork.security.SecurityUtils;
import com.codahale.metrics.annotation.Timed;
import artwork.domain.Multimedia;

import artwork.repository.MultimediaRepository;
import artwork.web.rest.util.HeaderUtil;
import artwork.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Multimedia.
 */
@RestController
@RequestMapping("/api")
public class MultimediaResource {

    private final Logger log = LoggerFactory.getLogger(MultimediaResource.class);

    private static final String ENTITY_NAME = "multimedia";

    private final MultimediaRepository multimediaRepository;
    private final UserRepository userRepository;

    public MultimediaResource(MultimediaRepository multimediaRepository, UserRepository userRepository) {
        this.multimediaRepository = multimediaRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /multimedias : Create a new multimedia.
     *
     * @param multimedia the multimedia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new multimedia, or with status 400 (Bad Request) if the multimedia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/multimedias")
    @Timed
    public ResponseEntity<Multimedia> createMultimedia(@Valid @RequestBody Multimedia multimedia) throws URISyntaxException {
        log.debug("REST request to save Multimedia : {}", multimedia);
        if (multimedia.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new multimedia cannot already have an ID")).body(null);
        }
        Multimedia result = multimediaRepository.save(multimedia);
        return ResponseEntity.created(new URI("/api/multimedias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /multimedias : Updates an existing multimedia.
     *
     * @param multimedia the multimedia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated multimedia,
     * or with status 400 (Bad Request) if the multimedia is not valid,
     * or with status 500 (Internal Server Error) if the multimedia couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/multimedias")
    @Timed
    public ResponseEntity<Multimedia> updateMultimedia(@Valid @RequestBody Multimedia multimedia) throws URISyntaxException {
        log.debug("REST request to update Multimedia : {}", multimedia);
        if (multimedia.getId() == null) {
            return createMultimedia(multimedia);
        }
        Multimedia result = multimediaRepository.save(multimedia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, multimedia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /multimedias : get all the multimedias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of multimedias in body
     */
    @GetMapping("/multimedias")
    @Timed
    public ResponseEntity<List<Multimedia>> getAllMultimedias(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Multimedias");
        Page<Multimedia> page = multimediaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/multimedias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /multimedias/:id : get the "id" multimedia.
     *
     * @param id the id of the multimedia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the multimedia, or with status 404 (Not Found)
     */
    @GetMapping("/multimedias/{id}")
    @Timed
    public ResponseEntity<Multimedia> getMultimedia(@PathVariable Long id) {
        log.debug("REST request to get Multimedia : {}", id);
        Multimedia multimedia = multimediaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(multimedia));
    }

    /**
     * DELETE  /multimedias/:id : delete the "id" multimedia.
     *
     * @param id the id of the multimedia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/multimedias/{id}")
    @Timed
    public ResponseEntity<Void> deleteMultimedia(@PathVariable Long id) {
        log.debug("REST request to delete Multimedia : {}", id);
        multimediaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     *
     * @param id
     * @param pageable
     * @return List<Multimedia>
     */
    @GetMapping(value = "/multimedia/user")
    @Timed
    public ResponseEntity<List<Multimedia>> getUserMultimedia(@RequestParam(required = false) Long id, Pageable pageable) {

        User user;

        if (id != null){
            user = userRepository.findOne(id);
            if (user == null) return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "id error",
                "no user found with this id")).body(null);
        }else{
            user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        }

        List<Multimedia> multimedias = multimediaRepository.findByUserIsCurrentUserOrderDesc(user.getLogin(), pageable);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "get"))
            .body(multimedias);
    }
}
