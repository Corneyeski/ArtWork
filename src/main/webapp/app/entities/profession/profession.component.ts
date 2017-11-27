import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Profession } from './profession.model';
import { ProfessionService } from './profession.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-profession',
    templateUrl: './profession.component.html'
})
export class ProfessionComponent implements OnInit, OnDestroy {
professions: Profession[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private professionService: ProfessionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.professionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.professions = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInProfessions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Profession) {
        return item.id;
    }
    registerChangeInProfessions() {
        this.eventSubscriber = this.eventManager.subscribe('professionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
