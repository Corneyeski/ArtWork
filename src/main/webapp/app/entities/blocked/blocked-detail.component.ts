import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Blocked } from './blocked.model';
import { BlockedService } from './blocked.service';

@Component({
    selector: 'jhi-blocked-detail',
    templateUrl: './blocked-detail.component.html'
})
export class BlockedDetailComponent implements OnInit, OnDestroy {

    blocked: Blocked;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private blockedService: BlockedService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBlockeds();
    }

    load(id) {
        this.blockedService.find(id).subscribe((blocked) => {
            this.blocked = blocked;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBlockeds() {
        this.eventSubscriber = this.eventManager.subscribe(
            'blockedListModification',
            (response) => this.load(this.blocked.id)
        );
    }
}
