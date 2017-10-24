import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { PricesT } from './prices-t.model';
import { PricesTService } from './prices-t.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-prices-t',
    templateUrl: './prices-t.component.html'
})
export class PricesTComponent implements OnInit, OnDestroy {
pricesTS: PricesT[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pricesTService: PricesTService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.pricesTService.query().subscribe(
            (res: ResponseWrapper) => {
                this.pricesTS = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPricesTS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PricesT) {
        return item.id;
    }
    registerChangeInPricesTS() {
        this.eventSubscriber = this.eventManager.subscribe('pricesTListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
