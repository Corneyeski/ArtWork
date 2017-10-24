import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { UserExt } from './user-ext.model';
import { UserExtPopupService } from './user-ext-popup.service';
import { UserExtService } from './user-ext.service';
import { User, UserService } from '../../shared';
import { City, CityService } from '../city';
import { Language, LanguageService } from '../language';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-ext-dialog',
    templateUrl: './user-ext-dialog.component.html'
})
export class UserExtDialogComponent implements OnInit {

    userExt: UserExt;
    isSaving: boolean;

    users: User[];

    cities: City[];

    languages: Language[];
    birthdateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private userExtService: UserExtService,
        private userService: UserService,
        private cityService: CityService,
        private languageService: LanguageService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.cityService.query()
            .subscribe((res: ResponseWrapper) => { this.cities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.languageService.query()
            .subscribe((res: ResponseWrapper) => { this.languages = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.userExt, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userExt.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userExtService.update(this.userExt));
        } else {
            this.subscribeToSaveResponse(
                this.userExtService.create(this.userExt));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserExt>) {
        result.subscribe((res: UserExt) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: UserExt) {
        this.eventManager.broadcast({ name: 'userExtListModification', content: 'OK'});
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

    trackCityById(index: number, item: City) {
        return item.id;
    }

    trackLanguageById(index: number, item: Language) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-ext-popup',
    template: ''
})
export class UserExtPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userExtPopupService: UserExtPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userExtPopupService
                    .open(UserExtDialogComponent as Component, params['id']);
            } else {
                this.userExtPopupService
                    .open(UserExtDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
