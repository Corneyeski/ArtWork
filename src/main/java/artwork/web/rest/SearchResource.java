package artwork.web.rest;

import artwork.domain.City;
import artwork.domain.Profession;
import artwork.domain.UserExt;
import artwork.repository.CityRepository;
import artwork.repository.ProfessionRepository;
import artwork.repository.UserCriteriaRepository;
import com.codahale.metrics.annotation.Timed;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SearchResource {

    @Inject
    private UserCriteriaRepository userCriteriaRepository;

    @Inject
    private CityRepository cityRepository;

    @Inject
    private ProfessionRepository professionRepository;

    @RequestMapping(value = "/search/users",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<List<UserExt>> searchUsers(
        @RequestParam(value = "city", required = false) String city,
        @RequestParam(value = "minPoints", required = false) Double minPopular,
        @RequestParam(value = "maxPoints", required = false) Double maxPopular,
        @RequestParam(value = "tags", required = false) String tags,
        @RequestParam(value = "validated", required = false) boolean validated,
        @RequestParam(value = "minAge", required = false) Integer ageMin,
        @RequestParam(value = "maxAge", required = false) Integer ageMax,
        @RequestParam(value = "kind", required = false) Integer kind,
        @RequestParam(value = "profession", required = false) String profession
    ) throws URISyntaxException {

        Map<String, Object> params = new HashMap<>();

        if (city != null && !city.equalsIgnoreCase("")) {

            City cityFind = cityRepository.findByName(city);

            params.put("city",cityFind);
        }
        if(maxPopular != null && maxPopular > 0.0 && maxPopular > minPopular){
            params.put("maxPopular",maxPopular);
        }
        if(minPopular != null && minPopular > 0.0){
            params.put("minPopular",minPopular);
        }
        if(tags != null && !tags.equals("")){
            params.put("tags",tags);
        }
        if(validated){
            params.put("validated",validated);
        }
        if(ageMin != null && ageMin > 0){
            params.put("minAge",ageMin);
        }
        if(ageMax != null && ageMax > 0){
            params.put("maxAge",ageMax);
        }

        if(kind != null) params.put("kind",kind);

        if(profession != null && !profession.equals("")) {

            Profession professionFind = professionRepository.findByName(profession);

            params.put("profession",professionFind);
        }

        List<UserExt> result = userCriteriaRepository.filterUserDefinitions(params);

        return new ResponseEntity<>(
            result,
            HttpStatus.OK);
    }
}
