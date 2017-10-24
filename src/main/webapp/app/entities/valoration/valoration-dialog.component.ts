import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Valoration } from './valoration.model';
import { ValorationPopupService } from './valoration-popup.service';
import { ValorationService } from './valoration.service';
import { User, UserService } from '../../shared';
import { Multimedia, MultimediaService } from '../multimedia';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-valoration-dialog',
    templateUrl: './valoration-dialog.component.html'
})
export class ValorationDialogComponent implements OnInit {

    valoration: Valoration;
    isSaving: boolean;

    users: User[];

    multimedias: Multimedia[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private valorationService: ValorationService,
        private userService: UserService,
        private multimediaService: MultimediaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.multimediaService.query()
            .subscribe((res: ResponseWrapper) => { this.multimedias = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.valoration.id !== undefined) {
            this.subscribeToSaveResponse(
                this.valorationService.update(this.valoration));
        } else {
            this.subscribeToSaveResponse(
                this.valorationService.create(this.valoration));
        }
    }

    private subscribeToSaveResponse(result: Observable<Valoration>) {
        result.subscribe((res: Valoration) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Valoration) {
        this.eventManager.broadcast({ name: 'valorationListModification', content: 'OK'});
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

    trackMultimediaById(index: number, item: Multimedia) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-valoration-popup',
    template: ''
})
export class ValorationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private valorationPopupService: ValorationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.valorationPopupService
                    .open(ValorationDialogComponent as Component, params['id']);
            } else {
                this.valorationPopupService
                    .open(ValorationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
