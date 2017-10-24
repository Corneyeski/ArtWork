package artwork.repository;

import artwork.domain.ReasonReport;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ReasonReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReasonReportRepository extends JpaRepository<ReasonReport, Long> {

}
