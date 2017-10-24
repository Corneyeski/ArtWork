package artwork.repository;

import artwork.domain.Report;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Report entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("select report from Report report where report.reporter.login = ?#{principal.username}")
    List<Report> findByReporterIsCurrentUser();

    @Query("select report from Report report where report.reported.login = ?#{principal.username}")
    List<Report> findByReportedIsCurrentUser();

}
