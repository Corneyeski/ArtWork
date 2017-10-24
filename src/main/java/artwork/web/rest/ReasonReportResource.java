package artwork.web.rest;

import com.codahale.metrics.annotation.Timed;
import artwork.domain.ReasonReport;

import artwork.repository.ReasonReportRepository;
import artwork.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ReasonReport.
 */
@RestController
@RequestMapping("/api")
public class ReasonReportResource {

    private final Logger log = LoggerFactory.getLogger(ReasonReportResource.class);

    private static final String ENTITY_NAME = "reasonReport";

    private final ReasonReportRepository reasonReportRepository;

    public ReasonReportResource(ReasonReportRepository reasonReportRepository) {
        this.reasonReportRepository = reasonReportRepository;
    }

    /**
     * POST  /reason-reports : Create a new reasonReport.
     *
     * @param reasonReport the reasonReport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reasonReport, or with status 400 (Bad Request) if the reasonReport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reason-reports")
    @Timed
    public ResponseEntity<ReasonReport> createReasonReport(@RequestBody ReasonReport reasonReport) throws URISyntaxException {
        log.debug("REST request to save ReasonReport : {}", reasonReport);
        if (reasonReport.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new reasonReport cannot already have an ID")).body(null);
        }
        ReasonReport result = reasonReportRepository.save(reasonReport);
        return ResponseEntity.created(new URI("/api/reason-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reason-reports : Updates an existing reasonReport.
     *
     * @param reasonReport the reasonReport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reasonReport,
     * or with status 400 (Bad Request) if the reasonReport is not valid,
     * or with status 500 (Internal Server Error) if the reasonReport couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reason-reports")
    @Timed
    public ResponseEntity<ReasonReport> updateReasonReport(@RequestBody ReasonReport reasonReport) throws URISyntaxException {
        log.debug("REST request to update ReasonReport : {}", reasonReport);
        if (reasonReport.getId() == null) {
            return createReasonReport(reasonReport);
        }
        ReasonReport result = reasonReportRepository.save(reasonReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reasonReport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reason-reports : get all the reasonReports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reasonReports in body
     */
    @GetMapping("/reason-reports")
    @Timed
    public List<ReasonReport> getAllReasonReports() {
        log.debug("REST request to get all ReasonReports");
        return reasonReportRepository.findAll();
        }

    /**
     * GET  /reason-reports/:id : get the "id" reasonReport.
     *
     * @param id the id of the reasonReport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reasonReport, or with status 404 (Not Found)
     */
    @GetMapping("/reason-reports/{id}")
    @Timed
    public ResponseEntity<ReasonReport> getReasonReport(@PathVariable Long id) {
        log.debug("REST request to get ReasonReport : {}", id);
        ReasonReport reasonReport = reasonReportRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reasonReport));
    }

    /**
     * DELETE  /reason-reports/:id : delete the "id" reasonReport.
     *
     * @param id the id of the reasonReport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reason-reports/{id}")
    @Timed
    public ResponseEntity<Void> deleteReasonReport(@PathVariable Long id) {
        log.debug("REST request to delete ReasonReport : {}", id);
        reasonReportRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
