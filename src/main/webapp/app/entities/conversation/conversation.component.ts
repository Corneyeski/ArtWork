import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Conversation } from './conversation.model';
import { ConversationService } from './conversation.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-conversation',
    templateUrl: './conversation.component.html'
})
export class ConversationComponent implements OnInit, OnDestroy {
conversations: Conversation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private conversationService: ConversationService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.conversationService.query().subscribe(
            (res: ResponseWrapper) => {
                this.conversations = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInConversations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Conversation) {
        return item.id;
    }
    registerChangeInConversations() {
        this.eventSubscriber = this.eventManager.subscribe('conversationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
