package artwork.repository;

import artwork.domain.UserProfileValoration;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the UserProfileValoration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserProfileValorationRepository extends JpaRepository<UserProfileValoration, Long> {

    @Query("select user_profile_valoration from UserProfileValoration user_profile_valoration where user_profile_valoration.valuator.login = ?#{principal.username}")
    List<UserProfileValoration> findByValuatorIsCurrentUser();

    @Query("select user_profile_valoration from UserProfileValoration user_profile_valoration where user_profile_valoration.valuated.login = ?#{principal.username}")
    List<UserProfileValoration> findByValuatedIsCurrentUser();

}
