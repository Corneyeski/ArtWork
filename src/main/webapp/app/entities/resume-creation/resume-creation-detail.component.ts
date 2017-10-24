import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { ResumeCreation } from './resume-creation.model';
import { ResumeCreationService } from './resume-creation.service';

@Component({
    selector: 'jhi-resume-creation-detail',
    templateUrl: './resume-creation-detail.component.html'
})
export class ResumeCreationDetailComponent implements OnInit, OnDestroy {

    resumeCreation: ResumeCreation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private resumeCreationService: ResumeCreationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInResumeCreations();
    }

    load(id) {
        this.resumeCreationService.find(id).subscribe((resumeCreation) => {
            this.resumeCreation = resumeCreation;
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

    registerChangeInResumeCreations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'resumeCreationListModification',
            (response) => this.load(this.resumeCreation.id)
        );
    }
}
