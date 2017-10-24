import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Offer } from './offer.model';
import { OfferService } from './offer.service';

@Component({
    selector: 'jhi-offer-detail',
    templateUrl: './offer-detail.component.html'
})
export class OfferDetailComponent implements OnInit, OnDestroy {

    offer: Offer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private offerService: OfferService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOffers();
    }

    load(id) {
        this.offerService.find(id).subscribe((offer) => {
            this.offer = offer;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOffers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'offerListModification',
            (response) => this.load(this.offer.id)
        );
    }
}
