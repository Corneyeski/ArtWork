package artwork.repository;

import artwork.domain.CommentLike;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the CommentLike entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    @Query("select comment_like from CommentLike comment_like where comment_like.user.login = ?#{principal.username}")
    List<CommentLike> findByUserIsCurrentUser();

}
