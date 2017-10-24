import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { UserProfileValoration } from './user-profile-valoration.model';
import { UserProfileValorationService } from './user-profile-valoration.service';

@Component({
    selector: 'jhi-user-profile-valoration-detail',
    templateUrl: './user-profile-valoration-detail.component.html'
})
export class UserProfileValorationDetailComponent implements OnInit, OnDestroy {

    userProfileValoration: UserProfileValoration;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userProfileValorationService: UserProfileValorationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserProfileValorations();
    }

    load(id) {
        this.userProfileValorationService.find(id).subscribe((userProfileValoration) => {
            this.userProfileValoration = userProfileValoration;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserProfileValorations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userProfileValorationListModification',
            (response) => this.load(this.userProfileValoration.id)
        );
    }
}
