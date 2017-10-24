package artwork.repository;

import artwork.domain.Following;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Following entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {

    @Query("select following from Following following where following.follower.login = ?#{principal.username}")
    List<Following> findByFollowerIsCurrentUser();

    @Query("select following from Following following where following.followed.login = ?#{principal.username}")
    List<Following> findByFollowedIsCurrentUser();

}
