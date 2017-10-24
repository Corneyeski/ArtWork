import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PricesT } from './prices-t.model';
import { PricesTService } from './prices-t.service';

@Component({
    selector: 'jhi-prices-t-detail',
    templateUrl: './prices-t-detail.component.html'
})
export class PricesTDetailComponent implements OnInit, OnDestroy {

    pricesT: PricesT;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pricesTService: PricesTService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPricesTS();
    }

    load(id) {
        this.pricesTService.find(id).subscribe((pricesT) => {
            this.pricesT = pricesT;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPricesTS() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pricesTListModification',
            (response) => this.load(this.pricesT.id)
        );
    }
}
