import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { City } from './city.model';
import { CityService } from './city.service';

@Component({
    selector: 'jhi-city-detail',
    templateUrl: './city-detail.component.html'
})
export class CityDetailComponent implements OnInit, OnDestroy {

    city: City;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private cityService: CityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCities();
    }

    load(id) {
        this.cityService.find(id).subscribe((city) => {
            this.city = city;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cityListModification',
            (response) => this.load(this.city.id)
        );
    }
}
