import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserProfileValoration } from './user-profile-valoration.model';
import { UserProfileValorationPopupService } from './user-profile-valoration-popup.service';
import { UserProfileValorationService } from './user-profile-valoration.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-profile-valoration-dialog',
    templateUrl: './user-profile-valoration-dialog.component.html'
})
export class UserProfileValorationDialogComponent implements OnInit {

    userProfileValoration: UserProfileValoration;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private userProfileValorationService: UserProfileValorationService,
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
        if (this.userProfileValoration.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userProfileValorationService.update(this.userProfileValoration));
        } else {
            this.subscribeToSaveResponse(
                this.userProfileValorationService.create(this.userProfileValoration));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserProfileValoration>) {
        result.subscribe((res: UserProfileValoration) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: UserProfileValoration) {
        this.eventManager.broadcast({ name: 'userProfileValorationListModification', content: 'OK'});
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
    selector: 'jhi-user-profile-valoration-popup',
    template: ''
})
export class UserProfileValorationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userProfileValorationPopupService: UserProfileValorationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userProfileValorationPopupService
                    .open(UserProfileValorationDialogComponent as Component, params['id']);
            } else {
                this.userProfileValorationPopupService
                    .open(UserProfileValorationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
