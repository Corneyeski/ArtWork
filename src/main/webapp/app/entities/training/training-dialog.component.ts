import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Training } from './training.model';
import { TrainingPopupService } from './training-popup.service';
import { TrainingService } from './training.service';

@Component({
    selector: 'jhi-training-dialog',
    templateUrl: './training-dialog.component.html'
})
export class TrainingDialogComponent implements OnInit {

    training: Training;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private trainingService: TrainingService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
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
        if (this.training.id !== undefined) {
            this.subscribeToSaveResponse(
                this.trainingService.update(this.training));
        } else {
            this.subscribeToSaveResponse(
                this.trainingService.create(this.training));
        }
    }

    private subscribeToSaveResponse(result: Observable<Training>) {
        result.subscribe((res: Training) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Training) {
        this.eventManager.broadcast({ name: 'trainingListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-training-popup',
    template: ''
})
export class TrainingPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private trainingPopupService: TrainingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.trainingPopupService
                    .open(TrainingDialogComponent as Component, params['id']);
            } else {
                this.trainingPopupService
                    .open(TrainingDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
