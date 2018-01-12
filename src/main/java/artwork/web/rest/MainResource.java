package artwork.web.rest;

import artwork.domain.Blocked;
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
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the main page
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

    @GetMapping("/main")
    @Transactional
    public ResponseEntity<MainRDTO> firstLogin() throws URISyntaxException {

        Optional<User> optionalUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());

        if (!optionalUser.isPresent()) return null;

        UserExt currentUser = userExtRepository.findOneByUser(optionalUser.get());
        User user = optionalUser.get();

        List<User> blockedUsers = blockedRepository.selectBlockedFindByBlock(user);

        blockedUsers.forEach(System.out::println);


        return null;
    }

}
