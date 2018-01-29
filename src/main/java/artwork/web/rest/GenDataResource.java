package artwork.web.rest;


import artwork.repository.MultimediaRepository;
import artwork.service.gendata.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class GenDataResource {


    private final MultimediaRepository multimediaRepository;

    public GenDataResource(MultimediaRepository multimediaRepository) {
        this.multimediaRepository = multimediaRepository;
    }

    @GetMapping("/genData")
    @Transactional
    public void genData(){
        Task t = new Task();

        multimediaRepository.save( t.genData());
    }
}
