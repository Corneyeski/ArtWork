package artwork.web.rest;

import artwork.domain.User;
import artwork.repository.FollowingRepository;
import artwork.repository.MultimediaRepository;
import artwork.repository.UserRepository;
import artwork.security.SecurityUtils;
import artwork.web.rest.rdto.ProfileRDTO;
import artwork.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * REST controller for managing profiles.
 */
@RestController
@RequestMapping("/api")
public class ProfileResource {

    private final Logger log = LoggerFactory.getLogger(PricesTResource.class);

    private static final String ENTITY_NAME = "profiles";

    private final UserRepository userRepository;
    private final MultimediaRepository multimediaRepository;
    private final FollowingRepository followingRepository;

    public ProfileResource(UserRepository userRepository,
                           MultimediaRepository multimediaRepository,
                           FollowingRepository followingRepository) {
        this.userRepository = userRepository;
        this.multimediaRepository = multimediaRepository;
        this.followingRepository = followingRepository;
    }


    //TODO Un metodo o dos?

   /* @GetMapping("/profile")
    @Timed
    public ResponseEntity<ProfileRDTO> userProfile() {



        return null;
    }*/

    @PostMapping("/profile")
    @Timed
    public ResponseEntity<ProfileRDTO> userProfile(@RequestParam(required = false) Long id) {

        User user = new User();

        if (id != null){
            user = userRepository.findOne(id);
            if (user == null) return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "id error",
                "no user found with this id")).body(null);
        }else{
            user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        }

        return null;
    }
}
