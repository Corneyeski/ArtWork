import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Message } from './message.model';
import { MessagePopupService } from './message-popup.service';
import { MessageService } from './message.service';
import { User, UserService } from '../../shared';
import { Conversation, ConversationService } from '../conversation';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-message-dialog',
    templateUrl: './message-dialog.component.html'
})
export class MessageDialogComponent implements OnInit {

    message: Message;
    isSaving: boolean;

    users: User[];

    conversations: Conversation[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private messageService: MessageService,
        private userService: UserService,
        private conversationService: ConversationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.conversationService.query()
            .subscribe((res: ResponseWrapper) => { this.conversations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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
        if (this.message.id !== undefined) {
            this.subscribeToSaveResponse(
                this.messageService.update(this.message));
        } else {
            this.subscribeToSaveResponse(
                this.messageService.create(this.message));
        }
    }

    private subscribeToSaveResponse(result: Observable<Message>) {
        result.subscribe((res: Message) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Message) {
        this.eventManager.broadcast({ name: 'messageListModification', content: 'OK'});
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

    trackConversationById(index: number, item: Conversation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-message-popup',
    template: ''
})
export class MessagePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private messagePopupService: MessagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.messagePopupService
                    .open(MessageDialogComponent as Component, params['id']);
            } else {
                this.messagePopupService
                    .open(MessageDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
