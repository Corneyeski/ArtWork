package artwork.repository;

import artwork.domain.*;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.ZonedDateTime;
import java.util.*;

@Repository
public class OfferCriteriaRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    UserExtRepository userExtRepository;

    private CriteriaBuilder builder;

    private CriteriaQuery<Offer> offerCriteriaQuery;

    private Root<Offer> offerRoot;

    @PostConstruct
    public void initCriteria() {
        builder = entityManager.getCriteriaBuilder();

        offerCriteriaQuery = builder.createQuery(Offer.class);

        offerRoot = offerCriteriaQuery.from(Offer.class);
    }

    public List<Offer> filterOfferDefinitions(Map<String, Object> parameters) {

        filterActivatedOffers();

        filterByUserName(parameters);

        filterByName(parameters);

        filterBySalary(parameters);

        filterByContract(parameters);

        filterByTags(parameters);

        filterByDate(parameters);

        return entityManager.createQuery( offerCriteriaQuery ).getResultList();
    }

    private void filterActivatedOffers(){
        offerCriteriaQuery.select(offerRoot);
        offerCriteriaQuery.where(builder.isTrue(offerRoot.get(Offer_.status)));
    }

    private void filterByUserName(Map<String, Object> parameters) {

        if(parameters.containsKey("username")){
            String searchName = (String) parameters.get("username");

            offerCriteriaQuery.select(offerRoot);
            offerCriteriaQuery.where(builder.like(offerRoot.get(Offer_.creator.getName()), "%" + searchName + "%"));
        }
    }
    private void filterByTags(Map<String, Object> parameters) {
        if (parameters.containsKey("tags")) {

            offerCriteriaQuery.select(offerRoot);

            String tags = (String) parameters.get("tags");

            String[] tag = tags.split("#");


            for(int i = 1; i < tag.length-1; i++){

                System.out.println(i + " " + tag);

                offerCriteriaQuery.where(builder.like(offerRoot.get(Offer_.tags), "%" + tag[i] + "%"));
            }
        }
    }
    private void filterByName(Map<String, Object> parameters) {

        if(parameters.containsKey("name")){
            String searchName = (String) parameters.get("name");

            offerCriteriaQuery.select(offerRoot);
            offerCriteriaQuery.where(builder.like(offerRoot.get(Offer_.name.getName()), "%" + searchName + "%"));
        }
    }
    private void filterBySalary(Map<String, Object> parameters) {
        if (parameters.containsKey("minSalary") || parameters.containsKey("maxSalary")) {

            offerCriteriaQuery.select(offerRoot);

            if (parameters.containsKey("minSalary") && parameters.containsKey("maxSalary")) {

                Double minSalary = (Double) parameters.get("minSalary");
                Double maxSalary = (Double) parameters.get("maxSalary");

                offerCriteriaQuery.where(builder.between(offerRoot.get(Offer_.salary), minSalary, maxSalary));
            }
            if (parameters.containsKey("minSalary") && !parameters.containsKey("maxSalary")) {

                Double minSalary = (Double) parameters.get("minSalary");

                offerCriteriaQuery.where(builder.greaterThanOrEqualTo(offerRoot.get(Offer_.salary), minSalary));
            }
            if (parameters.containsKey("maxSalary") && !parameters.containsKey("minSalary")) {

                Double maxSalary = (Double) parameters.get("maxSalary");

                offerCriteriaQuery.where(builder.lessThanOrEqualTo(offerRoot.get(Offer_.salary), maxSalary));
            }
        }
    }
    private void filterByContract(Map<String, Object> parameters) {

        if(parameters.containsKey("contract")){
            String contract = (String) parameters.get("contract");

            offerCriteriaQuery.select(offerRoot);
            offerCriteriaQuery.where(builder.like(offerRoot.get(Offer_.contract), "%" + contract + "%"));
        }
    }
    private void filterByDate(Map<String, Object> parameters) {

        if(parameters.containsKey("time")){
            ZonedDateTime time = (ZonedDateTime) parameters.get("time");

            offerCriteriaQuery.select(offerRoot);
            offerCriteriaQuery.where(builder.between(offerRoot.get(Offer_.time), time, ZonedDateTime.now()));
        }
    }

    public List<Offer> searchTags(String tags, Collection<Offer> offers){

        Map<String, Object> params = new HashMap<>();

        params.put("tags",tags);

        filterByTags(params);

        if (offers != null && !offers.isEmpty()) {

            Collection ids = new ArrayList();

            offers.forEach(o -> ids.add(o.getId()));

            offerCriteriaQuery.select(offerRoot);
            offerCriteriaQuery.where(builder.not(offerRoot.get(Offer_.id).in(offers)));
        }

        return entityManager.createQuery( offerCriteriaQuery ).getResultList();
    }
}
