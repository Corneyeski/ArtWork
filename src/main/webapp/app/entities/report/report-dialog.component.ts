import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Report } from './report.model';
import { ReportPopupService } from './report-popup.service';
import { ReportService } from './report.service';
import { User, UserService } from '../../shared';
import { Offer, OfferService } from '../offer';
import { Multimedia, MultimediaService } from '../multimedia';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-report-dialog',
    templateUrl: './report-dialog.component.html'
})
export class ReportDialogComponent implements OnInit {

    report: Report;
    isSaving: boolean;

    users: User[];

    offers: Offer[];

    multimedias: Multimedia[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private reportService: ReportService,
        private userService: UserService,
        private offerService: OfferService,
        private multimediaService: MultimediaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.offerService.query()
            .subscribe((res: ResponseWrapper) => { this.offers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.multimediaService.query()
            .subscribe((res: ResponseWrapper) => { this.multimedias = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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
        if (this.report.id !== undefined) {
            this.subscribeToSaveResponse(
                this.reportService.update(this.report));
        } else {
            this.subscribeToSaveResponse(
                this.reportService.create(this.report));
        }
    }

    private subscribeToSaveResponse(result: Observable<Report>) {
        result.subscribe((res: Report) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Report) {
        this.eventManager.broadcast({ name: 'reportListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackOfferById(index: number, item: Offer) {
        return item.id;
    }

    trackMultimediaById(index: number, item: Multimedia) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-report-popup',
    template: ''
})
export class ReportPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reportPopupService: ReportPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.reportPopupService
                    .open(ReportDialogComponent as Component, params['id']);
            } else {
                this.reportPopupService
                    .open(ReportDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
