package artwork.repository;

import artwork.domain.User;
import artwork.domain.UserExt;
import artwork.domain.UserExt_;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.inject.Inject;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Map;

@Repository
public class UserCriteriaRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    UserExtRepository userExtRepository;

    private CriteriaBuilder builder;

    private CriteriaQuery<UserExt> userExtCriteriaQuery;

    private Root<UserExt> userExtRoot;

    private CriteriaQuery<User> userCriteriaQuery;

    private Root<User> userRoot;

    @PostConstruct
    public void initCriteria(){
        builder = entityManager.getCriteriaBuilder();

        userExtCriteriaQuery = builder.createQuery( UserExt.class );

        userExtRoot = userExtCriteriaQuery.from( UserExt.class );

        userCriteriaQuery = builder.createQuery( User.class );

        userRoot = userCriteriaQuery.from(User.class);
    }

    private void filterByValidated(Map<String, Object> parameters) {
        if (parameters.containsKey("validated")) {
            boolean validated = (boolean) parameters.get("validated");

            userExtCriteriaQuery.select(userExtRoot);
            if (validated) {
                userExtCriteriaQuery.where(builder.isTrue(userExtRoot.get(UserExt_.validated)));
            }else{
                userExtCriteriaQuery.where(builder.isFalse(userExtRoot.get(UserExt_.validated)));
            }
        }
    }
}
