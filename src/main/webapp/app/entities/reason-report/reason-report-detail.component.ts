import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { ReasonReport } from './reason-report.model';
import { ReasonReportService } from './reason-report.service';

@Component({
    selector: 'jhi-reason-report-detail',
    templateUrl: './reason-report-detail.component.html'
})
export class ReasonReportDetailComponent implements OnInit, OnDestroy {

    reasonReport: ReasonReport;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private reasonReportService: ReasonReportService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReasonReports();
    }

    load(id) {
        this.reasonReportService.find(id).subscribe((reasonReport) => {
            this.reasonReport = reasonReport;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReasonReports() {
        this.eventSubscriber = this.eventManager.subscribe(
            'reasonReportListModification',
            (response) => this.load(this.reasonReport.id)
        );
    }
}
