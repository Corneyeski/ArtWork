import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Valoration } from './valoration.model';
import { ValorationService } from './valoration.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-valoration',
    templateUrl: './valoration.component.html'
})
export class ValorationComponent implements OnInit, OnDestroy {
valorations: Valoration[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private valorationService: ValorationService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.valorationService.query().subscribe(
            (res: ResponseWrapper) => {
                this.valorations = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInValorations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Valoration) {
        return item.id;
    }
    registerChangeInValorations() {
        this.eventSubscriber = this.eventManager.subscribe('valorationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
