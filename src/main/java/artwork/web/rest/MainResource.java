package artwork.web.rest;

import artwork.domain.*;
import artwork.domain.enumeration.Type;
import artwork.repository.*;
import artwork.security.SecurityUtils;
import artwork.web.rest.rdto.MainRDTO;
import artwork.web.rest.rdto.multimedia.MultimediaRDTO;
import artwork.web.rest.rdto.multimedia.NewMultimediaRDTO;
import artwork.web.rest.util.HeaderUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.net.URISyntaxException;
import java.util.*;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * Controller for the main page
 */
@RestController
@RequestMapping("/api")
public class MainResource {

    private final BlockedRepository blockedRepository;
    private final UserExtRepository userExtRepository;
    private final FollowingRepository followingRepository;
    private final MultimediaRepository multimediaRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    @Inject
    private OfferCriteriaRepository offerCriteriaRepository;

    public MainResource(BlockedRepository blockedRepository,
                        UserExtRepository userExtRepository,
                        FollowingRepository followingRepository,
                        MultimediaRepository multimediaRepository,
                        OfferRepository offerRepository,
                        UserRepository userRepository,
                        AlbumRepository albumRepository) {
        this.blockedRepository = blockedRepository;
        this.userExtRepository = userExtRepository;
        this.followingRepository = followingRepository;
        this.multimediaRepository = multimediaRepository;
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;
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
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "badLogin", "Necesitas estar logueado para usar esto")).body(null);

        String cityUser = userExtRepository.findCityByUser();
        Collection<User> blockedUsers = blockedRepository.selectBlockedFromCurrentUser();
        Collection<User> followedUsers = followingRepository.selectFollowedByCurrentUser();

        Page<Multimedia> filter = null;

        if(!blockedUsers.isEmpty() && !followedUsers.isEmpty()) {
            filter = multimediaRepository.
                findMultimediaPopularGreaterNoBlockedAndNoFollowed(cityUser, blockedUsers, followedUsers, pageable);
            filter.getContent().addAll(multimediaRepository.findMultimediaOfFollowing(followedUsers));
        }
        else if(blockedUsers.isEmpty() && !followedUsers.isEmpty()) {
            filter = multimediaRepository.
                findMultimediaPopularGreaterNoBlockedOrFollowed(cityUser, followedUsers, pageable);
            filter.getContent().addAll(multimediaRepository.findMultimediaOfFollowing(followedUsers));
        }
        else if(!blockedUsers.isEmpty() && followedUsers.isEmpty())
            filter = multimediaRepository.
                findMultimediaPopularGreaterNoBlockedOrFollowed(cityUser,blockedUsers,pageable);

        else if(blockedUsers.isEmpty() && followedUsers.isEmpty())
            filter = multimediaRepository.
                findMultimediaPopularGreaterThan(cityUser,pageable);


        blockedUsers.forEach(System.out::println);
        followedUsers.forEach(System.out::println);

        if(filter != null && filter.getContent() != null) {
            filter.getContent().forEach(System.out::println);

            MainRDTO result = new MainRDTO();

            filter.getContent().forEach(e -> {
                MultimediaRDTO m = new MultimediaRDTO();
                BeanUtils.copyProperties(e,m);
                result.getMultimedia().add(m);
            });

            result.getMultimedia()
                .stream().sorted(Comparator.comparing(MultimediaRDTO::getTime));

            System.gc();

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "set"))
                .body(result);



        } else return ResponseEntity.noContent()
            .headers(HeaderUtil.createAlert("empty content","empty")).build();
    }

    @GetMapping("/main/offers")
    @Transactional
    public ResponseEntity<Collection<Offer>> offersMain(Pageable pageable) throws URISyntaxException {

        UserExt user = userExtRepository.findOneByUser(
            userRepository.findOneByLogin(
                SecurityUtils.getCurrentUserLogin()).get());

        Collection<Offer> received = new ArrayList<>();

        received.addAll(offerCriteriaRepository.searchTags(user.getTags(), received));

        if(user.getProfession() != null) {
            received.addAll(offerRepository.findRecentOffersByProfessionAndStatusOrderByTimeDesc(
                user.getProfession(), received));
        }

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "set"))
            .body(received);
    }

    //TODO Añadir metodo para que puedan subir fotos/videos etc

    @PostMapping("/main/upload")
    @Transactional
    public ResponseEntity<Boolean> upload(@RequestBody NewMultimediaRDTO newMultimedia) {


        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();

        if(user == null) return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                "bad user", "usuario no valido")).body(null);



        Multimedia multimedia = new Multimedia();
        BeanUtils.copyProperties(newMultimedia, multimedia);

        if(newMultimedia.getAlbum() != null) {
            Album album = albumRepository.findOne(newMultimedia.getAlbum());
            if (album != null) multimedia.setAlbum(album);
        }

        multimedia.setUser(user);
        multimedia.setTotalValoration(0.0);

        if((multimedia.getType().equals(Type.SONG) && multimedia.getSong() != null && multimedia.getSongContentType() != null) ||
                (multimedia.getType().equals(Type.PHOTO) && multimedia.getImage() != null && multimedia.getImageContentType() != null)){

            multimediaRepository.save(multimedia);

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "set"))
                .body(true);
        }

        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME,
                "parametros incorrectos", "parametros incorrectos, algo no coincide")).body(null);
    }

    /*
    TODO metodo para obtener detalle de multimedia
    TODO metodo para obtener detalle de oferta
    (ESTO ES EL CRUD BASICO)
    */

    //TODO metodo para obtener detalle de publicidad (falta clase)

    //TODO REST para obtener notificaciones
}
