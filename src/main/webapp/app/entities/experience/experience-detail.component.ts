import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Experience } from './experience.model';
import { ExperienceService } from './experience.service';

@Component({
    selector: 'jhi-experience-detail',
    templateUrl: './experience-detail.component.html'
})
export class ExperienceDetailComponent implements OnInit, OnDestroy {

    experience: Experience;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private experienceService: ExperienceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInExperiences();
    }

    load(id) {
        this.experienceService.find(id).subscribe((experience) => {
            this.experience = experience;
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

    registerChangeInExperiences() {
        this.eventSubscriber = this.eventManager.subscribe(
            'experienceListModification',
            (response) => this.load(this.experience.id)
        );
    }
}
