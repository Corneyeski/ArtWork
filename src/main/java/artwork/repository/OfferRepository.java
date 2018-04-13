package artwork.repository;

import artwork.domain.Offer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
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

}
