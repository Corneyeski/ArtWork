package artwork.web.rest;


import artwork.repository.MultimediaRepository;
import artwork.repository.UserRepository;
import artwork.security.SecurityUtils;
import artwork.service.gendata.GenData;
import artwork.service.gendata.GenDataDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class GenDataResource {


    private final MultimediaRepository multimediaRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public GenDataResource(MultimediaRepository multimediaRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.multimediaRepository = multimediaRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/genData")
    @Transactional
    public void genData(){

        GenData t = new GenData();
        GenDataDTO gen = t.genData(userRepository.getLastId(),
            passwordEncoder.encode("user"),
            userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());

        userRepository.save(gen.getUsers());
        multimediaRepository.save(gen.getMultimedia());
    }
}
