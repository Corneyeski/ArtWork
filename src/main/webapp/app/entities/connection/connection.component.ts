import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Connection } from './connection.model';
import { ConnectionService } from './connection.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-connection',
    templateUrl: './connection.component.html'
})
export class ConnectionComponent implements OnInit, OnDestroy {
connections: Connection[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private connectionService: ConnectionService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.connectionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.connections = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInConnections();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Connection) {
        return item.id;
    }
    registerChangeInConnections() {
        this.eventSubscriber = this.eventManager.subscribe('connectionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
