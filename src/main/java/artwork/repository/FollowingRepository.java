package artwork.repository;

import artwork.domain.Following;
import artwork.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Collection;
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

    @Query("select following from Following following where following.follower.login = ?#{principal.username}")
    List<Following> findByFollowerIsCurrentUser(Pageable pageable);

    @Query("select following from Following following where following.followed.login = ?#{principal.username}")
    List<Following> findByFollowedIsCurrentUser(Pageable pageable);

    /**
     * Devuelve los usuarios seguidos del usuarioa ctual
     * @return Collection<User>
     */
    @Query("select following.followed from Following following where following.follower.login = ?#{principal.username}")
    Collection<User> selectFollowedByCurrentUser();
}
