package artwork.web.rest;

import artwork.domain.Following;
import artwork.domain.Multimedia;
import artwork.domain.User;
import artwork.repository.AlbumRepository;
import artwork.repository.FollowingRepository;
import artwork.repository.MultimediaRepository;
import artwork.repository.UserRepository;
import artwork.security.SecurityUtils;
import artwork.service.UserService;
import artwork.web.rest.rdto.ProfileRDTO;
import artwork.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;


/**
 * REST controller for managing profiles.
 */
@RestController
@RequestMapping("/api")
public class ProfileResource {

    private final Logger log = LoggerFactory.getLogger(PricesTResource.class);

    private static final String ENTITY_NAME = "profiles";

    private final UserService userService;
    private final MultimediaRepository multimediaRepository;
    private final FollowingRepository followingRepository;
    private final AlbumRepository albumRepository;

    public ProfileResource(UserService userService,
                           MultimediaRepository multimediaRepository,
                           FollowingRepository followingRepository,
                           AlbumRepository albumRepository) {
        this.userService = userService;
        this.multimediaRepository = multimediaRepository;
        this.followingRepository = followingRepository;
        this.albumRepository = albumRepository;
    }

    @GetMapping("/profile")
    @Timed
    public ResponseEntity<ProfileRDTO> userProfile(@RequestParam(required = false) Long id,
                                                   @Qualifier("album") Pageable album,
                                                   @Qualifier("multimedia") Pageable multimedia,
                                                   @Qualifier("followed") Pageable followed,
                                                   @Qualifier("follower") Pageable follower) {

        User user = userService.getUserByIdOrLogin(id);

        if (user == null)
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "id error",
            "no user found with this id")).body(null);

        List<Multimedia> multimedias = multimediaRepository.findByUserIsCurrentUserOrderDesc(user.getLogin(), multimedia);

        List<Following> followeds = followingRepository.findByFollowedIsCurrentUser(user.getLogin(), followed);

        List<Following> followers = followingRepository.findByFollowerIsCurrentUser(user.getLogin(), follower);


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "set"))
            .body(new ProfileRDTO(user,multimedias,followers,followeds, null));
    }
}
