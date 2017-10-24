import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Conversation } from './conversation.model';
import { ConversationPopupService } from './conversation-popup.service';
import { ConversationService } from './conversation.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-conversation-dialog',
    templateUrl: './conversation-dialog.component.html'
})
export class ConversationDialogComponent implements OnInit {

    conversation: Conversation;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private conversationService: ConversationService,
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
        if (this.conversation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.conversationService.update(this.conversation));
        } else {
            this.subscribeToSaveResponse(
                this.conversationService.create(this.conversation));
        }
    }

    private subscribeToSaveResponse(result: Observable<Conversation>) {
        result.subscribe((res: Conversation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Conversation) {
        this.eventManager.broadcast({ name: 'conversationListModification', content: 'OK'});
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
    selector: 'jhi-conversation-popup',
    template: ''
})
export class ConversationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private conversationPopupService: ConversationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.conversationPopupService
                    .open(ConversationDialogComponent as Component, params['id']);
            } else {
                this.conversationPopupService
                    .open(ConversationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
