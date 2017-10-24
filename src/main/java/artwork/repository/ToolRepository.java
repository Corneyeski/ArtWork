package artwork.repository;

import artwork.domain.Tool;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Tool entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {
    @Query("select distinct tool from Tool tool left join fetch tool.users")
    List<Tool> findAllWithEagerRelationships();

    @Query("select tool from Tool tool left join fetch tool.users where tool.id =:id")
    Tool findOneWithEagerRelationships(@Param("id") Long id);

}
