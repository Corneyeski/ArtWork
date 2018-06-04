package artwork.repository;

import artwork.domain.Multimedia;
import artwork.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA repository for the Multimedia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MultimediaRepository extends JpaRepository<Multimedia, Long> {

    /**
     *
     * @return List<Multimedia>
     */
    @Query("SELECT multimedia FROM Multimedia multimedia WHERE multimedia.user.login = ?#{principal.username}")
    List<Multimedia> findByUserIsCurrentUser();

    /**
     *
     * @return List<Multimedia>
     */
    @Query("SELECT multimedia FROM Multimedia multimedia WHERE multimedia.user.login = :user ORDER BY multimedia.time DESC")
    List<Multimedia> findByUserIsCurrentUserOrderDesc(String user, Pageable pageable);

    /**
     * Devuelve elementos multimedia filtrados por puntuacion y de usuarios de la misma ciudad ordenado de mas reciente a antiguo con paginacion
     * @param city
     * @param pageable
     * @return Page<Multimedia>
     */
    @Query("SELECT multimedia FROM Multimedia multimedia" +
        " WHERE multimedia.totalValoration > 2.9" +
        " AND multimedia.user.userExt.city.name = :city")
    Page<Multimedia> findMultimediaPopularGreaterThan(@Param("city")String city, Pageable pageable);
    /**
     * Devuelve elementos multimedia filtrados por puntuacion y de usuarios de la misma ciudad ordenado de mas reciente a antiguo y filtrando usuarios bloqueados y seguidos con paginacion
     * @param city
     * @param blocked
     * @param followed
     * @param pageable
     * @return Page<Multimedia>
     */
    @Query("SELECT multimedia FROM Multimedia multimedia " +
        "WHERE multimedia.totalValoration > 2.9 " +
        "AND multimedia.user.userExt.city.name = :city " +
        "AND multimedia.user NOT IN :blocked " +
        "AND multimedia.user NOT IN :followed ")
    Page<Multimedia> findMultimediaPopularGreaterNoBlockedAndNoFollowed(@Param("city")String city,
                                                                        @Param("blocked")Collection<User> blocked,
                                                                        @Param("followed")Collection<User> followed,
                                                                        Pageable pageable);
    /**
     * Devuelve elementos multimedia filtrados por puntuacion y de usuarios de la misma ciudad ordenado de mas reciente a antiguo y filtrando usuarios bloqueados o seguidos con paginacion
     * @param city
     * @param users
     * @param pageable
     * @return Page<Multimedia>
     */
    @Query("SELECT multimedia FROM Multimedia multimedia" +
        " WHERE multimedia.totalValoration > 2.9" +
        " AND multimedia.user.userExt.city.name = :city" +
        " AND multimedia.user NOT IN :users")
    Page<Multimedia> findMultimediaPopularGreaterNoBlockedOrFollowed(@Param("city")String city,
                                                           @Param("users")Collection<User> users,
                                                           Pageable pageable);

    /**
     * Devuelve elementos multimedia de los usuarios que sigues ordenado de mas reciente a antiguo
     * @param followedUsers
     * @return List<Multimedia>
     */
    @Query("SELECT multimedia FROM Multimedia multimedia" +
        " WHERE multimedia.user IN :followedUsers ")
    Collection<Multimedia> findMultimediaOfFollowing(@Param("followedUsers")Collection<User> followedUsers);
}
