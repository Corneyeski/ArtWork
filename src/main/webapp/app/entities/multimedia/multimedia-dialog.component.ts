import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Multimedia } from './multimedia.model';
import { MultimediaPopupService } from './multimedia-popup.service';
import { MultimediaService } from './multimedia.service';
import { User, UserService } from '../../shared';
import { Album, AlbumService } from '../album';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-multimedia-dialog',
    templateUrl: './multimedia-dialog.component.html'
})
export class MultimediaDialogComponent implements OnInit {

    multimedia: Multimedia;
    isSaving: boolean;

    users: User[];

    albums: Album[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private multimediaService: MultimediaService,
        private userService: UserService,
        private albumService: AlbumService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.albumService.query()
            .subscribe((res: ResponseWrapper) => { this.albums = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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
        this.dataUtils.clearInputImage(this.multimedia, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.multimedia.id !== undefined) {
            this.subscribeToSaveResponse(
                this.multimediaService.update(this.multimedia));
        } else {
            this.subscribeToSaveResponse(
                this.multimediaService.create(this.multimedia));
        }
    }

    private subscribeToSaveResponse(result: Observable<Multimedia>) {
        result.subscribe((res: Multimedia) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Multimedia) {
        this.eventManager.broadcast({ name: 'multimediaListModification', content: 'OK'});
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

    trackAlbumById(index: number, item: Album) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-multimedia-popup',
    template: ''
})
export class MultimediaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private multimediaPopupService: MultimediaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.multimediaPopupService
                    .open(MultimediaDialogComponent as Component, params['id']);
            } else {
                this.multimediaPopupService
                    .open(MultimediaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
