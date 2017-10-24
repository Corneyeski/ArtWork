package artwork.repository;

import artwork.domain.Multimedia;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Multimedia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MultimediaRepository extends JpaRepository<Multimedia, Long> {

    @Query("select multimedia from Multimedia multimedia where multimedia.user.login = ?#{principal.username}")
    List<Multimedia> findByUserIsCurrentUser();

}
