package artwork.repository;

import artwork.domain.PricesT;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PricesT entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PricesTRepository extends JpaRepository<PricesT, Long> {

}
