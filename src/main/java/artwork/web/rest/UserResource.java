package artwork.web.rest;

import artwork.config.Constants;
import artwork.domain.UserExt;
import artwork.repository.UserExtRepository;
import artwork.service.dto.NewUserDTO;
import artwork.web.rest.rdto.UserRDTO;
import com.codahale.metrics.annotation.Timed;
import artwork.domain.User;
import artwork.repository.UserRepository;
import artwork.security.AuthoritiesConstants;
import artwork.service.MailService;
import artwork.service.UserService;
import artwork.service.dto.UserDTO;
import artwork.web.rest.vm.ManagedUserVM;
import artwork.web.rest.util.HeaderUtil;
import artwork.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "userManagement";

    private final UserRepository userRepository;

    private final MailService mailService;

    private final UserService userService;

    private final UserExtRepository userExtRepository;

    public UserResource(UserRepository userRepository, MailService mailService,
                        UserService userService, UserExtRepository userExtRepository) {

        this.userRepository = userRepository;
        this.mailService = mailService;
        this.userService = userService;
        this.userExtRepository = userExtRepository;
    }

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param managedUserVM the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    /*@PostMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity createUser(@Valid @RequestBody ManagedUserVM managedUserVM) throws URISyntaxException {
        log.debug("REST request to save User : {}", managedUserVM);

        if (managedUserVM.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user cannot already have an ID"))
                .body(null);
        // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use"))
                .body(null);
        } else if (userRepository.findOneByEmail(managedUserVM.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use"))
                .body(null);
        } else {
            User newUser = userService.createUser(managedUserVM);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert( "userManagement.created", newUser.getLogin()))
                .body(newUser);
        }
    }*/

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param newUserDTO the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity createUserWithExt(@Valid @RequestBody NewUserDTO newUserDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", newUserDTO);

        //TODO Añadir Profesion al registro(?)
        //TODO Añadir metodos para comprobar si login o email ya estan en uso(?)

        System.out.println("POR QUE NO ENTRA EN EL DEBUG");

         if (userRepository.findOneByLogin(newUserDTO.getLogin().toLowerCase()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use"))
                .body(null);
        } else if (userRepository.findOneByEmail(newUserDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use"))
                .body(null);
        } else {
            User newUser = userService.createUser(newUserDTO);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert( "userManagement.created", newUser.getLogin()))
                .body(newUser);
        }
    }

    /**
     * PUT  /users : Updates an existing User.
     *
     * @param managedUserVM the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user,
     * or with status 400 (Bad Request) if the login or email is already in use,
     * or with status 500 (Internal Server Error) if the user couldn't be updated
     */
    @PutMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody ManagedUserVM managedUserVM) {
        log.debug("REST request to update User : {}", managedUserVM);
        Optional<User> existingUser = userRepository.findOneByEmail(managedUserVM.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserVM.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use")).body(null);
        }
        existingUser = userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserVM.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use")).body(null);
        }
        Optional<UserDTO> updatedUser = userService.updateUser(managedUserVM);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("userManagement.updated", managedUserVM.getLogin()));
    }

    /**
     * GET  /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/users")
    @Timed
    public ResponseEntity<List<UserDTO>> getAllUsers(@ApiParam Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * @return a string list of the all of the roles
     */
    @GetMapping("/users/authorities")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    /**
     * GET  /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDTO::new));
    }

    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "userManagement.deleted", login)).build();
    }

    @GetMapping("/users/user")
    @Timed
    public ResponseEntity<User> getFollowingUser(@RequestParam(required = false) Long id) {

        User user = userService.getUserByIdOrLogin(id);

        if (user != null){

            if (user.getUserExt() != null) user.getUserExt().setUser(null);

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "get"))
                .body(user);
        }else
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "id error",
                "no user found with this id")).body(null);
    }

    @PutMapping("/users/update")
    @Timed
    public ResponseEntity<User> updateUser(@RequestBody UserRDTO user) {

        if (user.getId() != null && user.getIdExt() != null){

            User u = userRepository.findOne(user.getId());
            UserExt ue = userExtRepository.findOne(user.getIdExt());

            if(u == null || ue == null)
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "id error",
                "no user found with this id")).body(null);

            BeanUtils.copyProperties(user, u);
            BeanUtils.copyProperties(user, ue);

            u.setUserExt(ue);
            userRepository.save(u);

            ue.setUser(u);
            userExtRepository.save(ue);

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "get"))
                .body(u);
        }
        return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "id error",
            "faltan los id")).body(null);
    }

    @GetMapping("/users/user-ext/{userID}")
    @Timed
    public ResponseEntity<UserExt> getUserExt(@PathVariable(required = true) Long userID) {

        UserExt userExt = userRepository.findUserExtByUserId(userID);

        if (userExt != null){
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("userExtManagement", "get"))
                .body(userExt);

        }else
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "id error",
                "no user found with this id")).body(null);
    }
}
