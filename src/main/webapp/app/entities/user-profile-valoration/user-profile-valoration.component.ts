import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { UserProfileValoration } from './user-profile-valoration.model';
import { UserProfileValorationService } from './user-profile-valoration.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-user-profile-valoration',
    templateUrl: './user-profile-valoration.component.html'
})
export class UserProfileValorationComponent implements OnInit, OnDestroy {
userProfileValorations: UserProfileValoration[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userProfileValorationService: UserProfileValorationService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.userProfileValorationService.query().subscribe(
            (res: ResponseWrapper) => {
                this.userProfileValorations = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserProfileValorations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UserProfileValoration) {
        return item.id;
    }
    registerChangeInUserProfileValorations() {
        this.eventSubscriber = this.eventManager.subscribe('userProfileValorationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
