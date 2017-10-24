import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Valoration } from './valoration.model';
import { ValorationService } from './valoration.service';

@Component({
    selector: 'jhi-valoration-detail',
    templateUrl: './valoration-detail.component.html'
})
export class ValorationDetailComponent implements OnInit, OnDestroy {

    valoration: Valoration;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private valorationService: ValorationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInValorations();
    }

    load(id) {
        this.valorationService.find(id).subscribe((valoration) => {
            this.valoration = valoration;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInValorations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'valorationListModification',
            (response) => this.load(this.valoration.id)
        );
    }
}
