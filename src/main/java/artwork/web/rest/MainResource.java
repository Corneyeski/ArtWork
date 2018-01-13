package artwork.web.rest;

import artwork.domain.Multimedia;
import artwork.domain.User;
import artwork.repository.*;
import artwork.security.SecurityUtils;
import artwork.web.rest.rdto.main.MainRDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.net.URISyntaxException;
import java.util.Collection;

/**
 * Controller for the main page
 */
@RestController
@RequestMapping("/api")
public class MainResource {

    private final BlockedRepository blockedRepository;
    private final UserRepository userRepository;
    private final UserExtRepository userExtRepository;
    private final FollowingRepository followingRepository;
    private final MultimediaRepository multimediaRepository;

    public MainResource(BlockedRepository blockedRepository, UserRepository userRepository, UserExtRepository userExtRepository, FollowingRepository followingRepository, MultimediaRepository multimediaRepository) {
        this.blockedRepository = blockedRepository;
        this.userRepository = userRepository;
        this.userExtRepository = userExtRepository;
        this.followingRepository = followingRepository;
        this.multimediaRepository = multimediaRepository;
    }

    /**
     * Method of main page
     * @return ResponseEntity<MainRDTO>
     * @throws URISyntaxException
     */
    @GetMapping("/main")
    @Transactional
    public ResponseEntity<MainRDTO> firstLogin(Pageable pageable) throws URISyntaxException {

        if (SecurityUtils.getCurrentUserLogin() == null)
            //TODO añadir mensaje: 'necesitas estar logueado'
            return null;

        /*
        Optional<User> optionalUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        UserExt currentUser = userExtRepository.findOneByUser(optionalUser.get());
        User user = optionalUser.get();
        */

        String cityUser = userExtRepository.findCityByUser();

        Collection<User> blockedUsers = blockedRepository.selectBlockedFromCurrentUser();
        Collection<User> followedUsers = followingRepository.selectFollowedByCurrentUser();

        Page<Multimedia> filter = null;

        if(!blockedUsers.isEmpty() && !followedUsers.isEmpty())
            filter = multimediaRepository.
                findMultimediaPopularGreaterNoBlockedAndNoFollowed(cityUser,blockedUsers,followedUsers,pageable);
        else if(blockedUsers.isEmpty() && !followedUsers.isEmpty())
            filter = multimediaRepository.
                findMultimediaPopularGreaterNoBlockedOrFollowed(cityUser,followedUsers,pageable);
        else if(!blockedUsers.isEmpty() && followedUsers.isEmpty())
            filter = multimediaRepository.
                findMultimediaPopularGreaterNoBlockedOrFollowed(cityUser,blockedUsers,pageable);
        else if(blockedUsers.isEmpty() && followedUsers.isEmpty())
            filter = multimediaRepository.
                findMultimediaPopularGreaterThan("",pageable);

        blockedUsers.forEach(System.out::println);
        followedUsers.forEach(System.out::println);
        if(filter != null && filter.getContent() != null)filter.getContent().forEach(System.out::println);




        return null;
    }

    //TODO metodo para obtener detalle de multimedia

    //TODO metodo para obtener detalle de oferta

    //TODO metodo para obtener detalle de publicidad (falta clase)
}
