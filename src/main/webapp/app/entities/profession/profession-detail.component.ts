import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Profession } from './profession.model';
import { ProfessionService } from './profession.service';

@Component({
    selector: 'jhi-profession-detail',
    templateUrl: './profession-detail.component.html'
})
export class ProfessionDetailComponent implements OnInit, OnDestroy {

    profession: Profession;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private professionService: ProfessionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProfessions();
    }

    load(id) {
        this.professionService.find(id).subscribe((profession) => {
            this.profession = profession;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProfessions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'professionListModification',
            (response) => this.load(this.profession.id)
        );
    }
}
