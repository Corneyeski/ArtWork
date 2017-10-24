package artwork.repository;

import artwork.domain.ResumeCreation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ResumeCreation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResumeCreationRepository extends JpaRepository<ResumeCreation, Long> {

}
