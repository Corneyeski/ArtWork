package artwork.repository;

import artwork.domain.Connection;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Connection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    @Query("select connection from Connection connection where connection.sender.login = ?#{principal.username}")
    List<Connection> findBySenderIsCurrentUser();

    @Query("select connection from Connection connection where connection.receiver.login = ?#{principal.username}")
    List<Connection> findByReceiverIsCurrentUser();

}
