package artwork.repository;

import artwork.domain.Valoration;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Valoration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValorationRepository extends JpaRepository<Valoration, Long> {

    @Query("select valoration from Valoration valoration where valoration.user.login = ?#{principal.username}")
    List<Valoration> findByUserIsCurrentUser();

}
