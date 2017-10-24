import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ReasonReport } from './reason-report.model';
import { ReasonReportPopupService } from './reason-report-popup.service';
import { ReasonReportService } from './reason-report.service';
import { Report, ReportService } from '../report';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-reason-report-dialog',
    templateUrl: './reason-report-dialog.component.html'
})
export class ReasonReportDialogComponent implements OnInit {

    reasonReport: ReasonReport;
    isSaving: boolean;

    reports: Report[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private reasonReportService: ReasonReportService,
        private reportService: ReportService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.reportService.query()
            .subscribe((res: ResponseWrapper) => { this.reports = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.reasonReport.id !== undefined) {
            this.subscribeToSaveResponse(
                this.reasonReportService.update(this.reasonReport));
        } else {
            this.subscribeToSaveResponse(
                this.reasonReportService.create(this.reasonReport));
        }
    }

    private subscribeToSaveResponse(result: Observable<ReasonReport>) {
        result.subscribe((res: ReasonReport) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ReasonReport) {
        this.eventManager.broadcast({ name: 'reasonReportListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackReportById(index: number, item: Report) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-reason-report-popup',
    template: ''
})
export class ReasonReportPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reasonReportPopupService: ReasonReportPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.reasonReportPopupService
                    .open(ReasonReportDialogComponent as Component, params['id']);
            } else {
                this.reasonReportPopupService
                    .open(ReasonReportDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
