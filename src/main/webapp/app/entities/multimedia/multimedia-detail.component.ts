import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Multimedia } from './multimedia.model';
import { MultimediaService } from './multimedia.service';

@Component({
    selector: 'jhi-multimedia-detail',
    templateUrl: './multimedia-detail.component.html'
})
export class MultimediaDetailComponent implements OnInit, OnDestroy {

    multimedia: Multimedia;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private multimediaService: MultimediaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMultimedias();
    }

    load(id) {
        this.multimediaService.find(id).subscribe((multimedia) => {
            this.multimedia = multimedia;
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

    registerChangeInMultimedias() {
        this.eventSubscriber = this.eventManager.subscribe(
            'multimediaListModification',
            (response) => this.load(this.multimedia.id)
        );
    }
}
