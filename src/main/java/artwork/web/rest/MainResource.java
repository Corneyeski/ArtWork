package artwork.web.rest;

import artwork.domain.User;
import artwork.domain.UserExt;
import artwork.repository.BlockedRepository;
import artwork.repository.UserExtRepository;
import artwork.repository.UserRepository;
import artwork.security.SecurityUtils;
import artwork.web.rest.rdto.main.MainRDTO;
import com.codahale.metrics.annotation.Timed;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

/**
 *  Controller for the main page
 */
@RestController
@RequestMapping("/api")
public class MainResource {

    private final BlockedRepository blockedRepository;

    private final UserRepository userRepository;

    private final UserExtRepository userExtRepository;

    public MainResource(BlockedRepository blockedRepository, UserRepository userRepository, UserExtRepository userExtRepository) {
        this.blockedRepository = blockedRepository;
        this.userRepository = userRepository;
        this.userExtRepository = userExtRepository;
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<MainRDTO> firstLogin(){

        Optional<User> optionalUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());


            if(!optionalUser.isPresent()){

            }

        UserExt currentUser = userExtRepository.findOneByUser(optionalUser.get());

        List<User> blockedUsers = blockedRepository.findBlockedByBlock(currentUser.getUser());

        for(User u : blockedUsers){
            System.out.println(u.getLogin());
        }

        return null;
    }

}
