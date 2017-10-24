package artwork.web.rest;

import com.codahale.metrics.annotation.Timed;
import artwork.domain.UserProfileValoration;

import artwork.repository.UserProfileValorationRepository;
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
 * REST controller for managing UserProfileValoration.
 */
@RestController
@RequestMapping("/api")
public class UserProfileValorationResource {

    private final Logger log = LoggerFactory.getLogger(UserProfileValorationResource.class);

    private static final String ENTITY_NAME = "userProfileValoration";

    private final UserProfileValorationRepository userProfileValorationRepository;

    public UserProfileValorationResource(UserProfileValorationRepository userProfileValorationRepository) {
        this.userProfileValorationRepository = userProfileValorationRepository;
    }

    /**
     * POST  /user-profile-valorations : Create a new userProfileValoration.
     *
     * @param userProfileValoration the userProfileValoration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userProfileValoration, or with status 400 (Bad Request) if the userProfileValoration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-profile-valorations")
    @Timed
    public ResponseEntity<UserProfileValoration> createUserProfileValoration(@Valid @RequestBody UserProfileValoration userProfileValoration) throws URISyntaxException {
        log.debug("REST request to save UserProfileValoration : {}", userProfileValoration);
        if (userProfileValoration.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userProfileValoration cannot already have an ID")).body(null);
        }
        UserProfileValoration result = userProfileValorationRepository.save(userProfileValoration);
        return ResponseEntity.created(new URI("/api/user-profile-valorations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-profile-valorations : Updates an existing userProfileValoration.
     *
     * @param userProfileValoration the userProfileValoration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userProfileValoration,
     * or with status 400 (Bad Request) if the userProfileValoration is not valid,
     * or with status 500 (Internal Server Error) if the userProfileValoration couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-profile-valorations")
    @Timed
    public ResponseEntity<UserProfileValoration> updateUserProfileValoration(@Valid @RequestBody UserProfileValoration userProfileValoration) throws URISyntaxException {
        log.debug("REST request to update UserProfileValoration : {}", userProfileValoration);
        if (userProfileValoration.getId() == null) {
            return createUserProfileValoration(userProfileValoration);
        }
        UserProfileValoration result = userProfileValorationRepository.save(userProfileValoration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userProfileValoration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-profile-valorations : get all the userProfileValorations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userProfileValorations in body
     */
    @GetMapping("/user-profile-valorations")
    @Timed
    public List<UserProfileValoration> getAllUserProfileValorations() {
        log.debug("REST request to get all UserProfileValorations");
        return userProfileValorationRepository.findAll();
        }

    /**
     * GET  /user-profile-valorations/:id : get the "id" userProfileValoration.
     *
     * @param id the id of the userProfileValoration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userProfileValoration, or with status 404 (Not Found)
     */
    @GetMapping("/user-profile-valorations/{id}")
    @Timed
    public ResponseEntity<UserProfileValoration> getUserProfileValoration(@PathVariable Long id) {
        log.debug("REST request to get UserProfileValoration : {}", id);
        UserProfileValoration userProfileValoration = userProfileValorationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userProfileValoration));
    }

    /**
     * DELETE  /user-profile-valorations/:id : delete the "id" userProfileValoration.
     *
     * @param id the id of the userProfileValoration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-profile-valorations/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserProfileValoration(@PathVariable Long id) {
        log.debug("REST request to delete UserProfileValoration : {}", id);
        userProfileValorationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
