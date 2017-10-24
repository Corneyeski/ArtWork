import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Blocked } from './blocked.model';
import { BlockedPopupService } from './blocked-popup.service';
import { BlockedService } from './blocked.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-blocked-dialog',
    templateUrl: './blocked-dialog.component.html'
})
export class BlockedDialogComponent implements OnInit {

    blocked: Blocked;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private blockedService: BlockedService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.blocked.id !== undefined) {
            this.subscribeToSaveResponse(
                this.blockedService.update(this.blocked));
        } else {
            this.subscribeToSaveResponse(
                this.blockedService.create(this.blocked));
        }
    }

    private subscribeToSaveResponse(result: Observable<Blocked>) {
        result.subscribe((res: Blocked) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Blocked) {
        this.eventManager.broadcast({ name: 'blockedListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-blocked-popup',
    template: ''
})
export class BlockedPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private blockedPopupService: BlockedPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.blockedPopupService
                    .open(BlockedDialogComponent as Component, params['id']);
            } else {
                this.blockedPopupService
                    .open(BlockedDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
