package artwork.repository;

import artwork.domain.Blocked;
import artwork.domain.User;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA repository for the Blocked entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlockedRepository extends JpaRepository<Blocked, Long> {

    @Query("select blocked from Blocked blocked where blocked.block.login = ?#{principal.username}")
    List<Blocked> findByBlockIsCurrentUser();

    @Query("select blocked from Blocked blocked where blocked.blocked.login = ?#{principal.username}")
    List<Blocked> findByBlockedIsCurrentUser();

    /**
     * Devuelve los usuarios bloqueados del usuarioa ctual
     * @return Collection<User>
     */
    @Query("select blocked.blocked from Blocked blocked where blocked.block.login = ?#{principal.username}")
    Collection<User> selectBlockedFromCurrentUser();

    List<Blocked> findBlockedByBlock(User user);

}
