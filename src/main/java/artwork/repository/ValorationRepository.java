package artwork.repository;

import artwork.domain.Multimedia;
import artwork.domain.User;
import artwork.domain.Valoration;
import org.springframework.data.repository.query.Param;
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

    Valoration findByUserAndMultimedia(User user, Multimedia multimedia);

    @Query("select AVG(valoration.mark) FROM Valoration valoration WHERE valoration.multimedia = :multimedia")
    Double avgMultimedia(@Param("multimedia")Multimedia multimedia);
}
