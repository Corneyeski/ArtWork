import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Language } from './language.model';
import { LanguageService } from './language.service';

@Component({
    selector: 'jhi-language-detail',
    templateUrl: './language-detail.component.html'
})
export class LanguageDetailComponent implements OnInit, OnDestroy {

    language: Language;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private languageService: LanguageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLanguages();
    }

    load(id) {
        this.languageService.find(id).subscribe((language) => {
            this.language = language;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLanguages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'languageListModification',
            (response) => this.load(this.language.id)
        );
    }
}
