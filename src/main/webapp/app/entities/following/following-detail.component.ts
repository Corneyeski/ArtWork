import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Following } from './following.model';
import { FollowingService } from './following.service';

@Component({
    selector: 'jhi-following-detail',
    templateUrl: './following-detail.component.html'
})
export class FollowingDetailComponent implements OnInit, OnDestroy {

    following: Following;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private followingService: FollowingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFollowings();
    }

    load(id) {
        this.followingService.find(id).subscribe((following) => {
            this.following = following;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFollowings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'followingListModification',
            (response) => this.load(this.following.id)
        );
    }
}
