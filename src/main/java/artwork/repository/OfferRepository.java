package artwork.repository;

import artwork.domain.Offer;
import artwork.domain.Profession;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA repository for the Offer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("select offer from Offer offer where offer.creator.login = ?#{principal.username}")
    List<Offer> findByCreatorIsCurrentUser();
    @Query("select distinct offer from Offer offer left join fetch offer.userExts")
    List<Offer> findAllWithEagerRelationships();

    @Query("select offer from Offer offer left join fetch offer.userExts where offer.id =:id")
    Offer findOneWithEagerRelationships(@Param("id") Long id);

//    //TODO Añadir paginacion
//    @Query("select offer from Offer offer where offer.status = true and offer.profession = :profession order by offer.time desc")
//    Collection<Offer> findRecentOffersByProfessionAndStatusOrderByTimeDesc(@Param("profession") Profession profession);

    Collection<Offer> findOffersByProfessionAndStatusOrderByTimeDesc(Profession profession,boolean status);

    @Query("select offer from Offer offer where offer.status = true" +
        " and offer.tags LIKE %:tag%" +
        " and offer not in :offersReceived order by offer.time desc")
    Collection<Offer> findRecentOffersByTags(@Param("tag") String tag, @Param("offersReceived") Collection<Offer> offers);

    @Query("select offer from Offer offer where offer.status = true" +
        " and offer.tags like %:tag%" +
        " order by offer.time desc")
    Collection<Offer> findRecentOffersByTagsNoOffers(@Param("tag") String tag);

    //TODO Añadir paginacion
    @Query("select offer from Offer offer where offer.status = true and offer.profession = :profession order by offer.time desc")
    Collection<Offer> findRecentOffers(@Param("profession") Profession profession);
}
