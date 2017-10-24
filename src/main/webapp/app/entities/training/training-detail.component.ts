import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Training } from './training.model';
import { TrainingService } from './training.service';

@Component({
    selector: 'jhi-training-detail',
    templateUrl: './training-detail.component.html'
})
export class TrainingDetailComponent implements OnInit, OnDestroy {

    training: Training;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private trainingService: TrainingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTrainings();
    }

    load(id) {
        this.trainingService.find(id).subscribe((training) => {
            this.training = training;
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

    registerChangeInTrainings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'trainingListModification',
            (response) => this.load(this.training.id)
        );
    }
}
