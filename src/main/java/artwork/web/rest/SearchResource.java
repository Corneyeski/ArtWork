package artwork.web.rest;

import artwork.domain.UserExt;
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

    @RequestMapping(value = "/search/users",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<List<UserExt>> searchUsers(
        @RequestParam(value = "city", required = false) String city,
        @RequestParam(value = "minPoints", required = false) Double minPopular,
        @RequestParam(value = "maxPoints", required = false) Double maxPopular,
        @RequestParam(value = "tags", required = false) String tags,
        @RequestParam(value = "validated", required = false) boolean validated,
        @RequestParam(value = "ageMin", required = false) Integer ageMin,
        @RequestParam(value = "ageMax", required = false) Integer ageMax
    ) throws URISyntaxException {

        Map<String, Object> params = new HashMap<>();

        if (city != null && !city.equalsIgnoreCase("")) {
            params.put("city",city);
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
            params.put("agemin",ageMin);
        }
        if(ageMax != null && ageMax > 0){
            params.put("agemax",ageMax);
        }

        List<UserExt> result = userCriteriaRepository.filterUserDefinitions(params);

        return new ResponseEntity<>(
            result,
            HttpStatus.OK);
    }
}
