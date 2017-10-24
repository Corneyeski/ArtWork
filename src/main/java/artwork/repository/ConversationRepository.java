package artwork.repository;

import artwork.domain.Conversation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Conversation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("select conversation from Conversation conversation where conversation.userOne.login = ?#{principal.username}")
    List<Conversation> findByUserOneIsCurrentUser();

    @Query("select conversation from Conversation conversation where conversation.userTwo.login = ?#{principal.username}")
    List<Conversation> findByUserTwoIsCurrentUser();

}
