package artwork.repository;

import artwork.domain.Multimedia;
import artwork.domain.User;
import artwork.domain.UserExt;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import artwork.domain.Multimedia_;

@Repository
public class MultimediaCriteriaRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    MultimediaRepository multimediaRepository;

    private CriteriaBuilder builder;

    private CriteriaQuery<Multimedia> multimediaCriteriaQuery;

    private Root<Multimedia> multimediaRoot;

    @PostConstruct
    public void initCriteria(){
        builder = entityManager.getCriteriaBuilder();

        multimediaCriteriaQuery = builder.createQuery( Multimedia.class );

        multimediaRoot = multimediaCriteriaQuery.from( Multimedia.class );
    }


    public List<Multimedia> filterMultimediaDefinitions(Map<String, Object> parameters) {

        filterByUserName(parameters);

        filterByTags(parameters);

        return entityManager.createQuery( multimediaCriteriaQuery ).getResultList();
    }

    private void filterByUserName(Map<String, Object> parameters) {

        if(parameters.containsKey("username")){
            String searchName = (String) parameters.get("username");

            multimediaCriteriaQuery.select(multimediaRoot);
            multimediaCriteriaQuery.where(builder.like(multimediaRoot.get(Multimedia_.user.getName()), "%" + searchName + "%"));
        }
    }
    private void filterByTags(Map<String, Object> parameters) {
        if (parameters.containsKey("tags")) {

            multimediaCriteriaQuery.select(multimediaRoot);

            String tags = (String) parameters.get("tags");

            String[] tag = tags.split("#");

            System.out.println(tag);

            for(int i = 1; i < tag.length-1; i++){
                multimediaCriteriaQuery.where(builder.like(multimediaRoot.get(Multimedia_.tags), "%" + tag[i] + "%"));
            }
        }
    }
}
