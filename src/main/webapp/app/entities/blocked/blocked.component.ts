import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Blocked } from './blocked.model';
import { BlockedService } from './blocked.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-blocked',
    templateUrl: './blocked.component.html'
})
export class BlockedComponent implements OnInit, OnDestroy {
blockeds: Blocked[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private blockedService: BlockedService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.blockedService.query().subscribe(
            (res: ResponseWrapper) => {
                this.blockeds = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBlockeds();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Blocked) {
        return item.id;
    }
    registerChangeInBlockeds() {
        this.eventSubscriber = this.eventManager.subscribe('blockedListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
