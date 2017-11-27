import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Profession } from './profession.model';
import { ProfessionPopupService } from './profession-popup.service';
import { ProfessionService } from './profession.service';

@Component({
    selector: 'jhi-profession-dialog',
    templateUrl: './profession-dialog.component.html'
})
export class ProfessionDialogComponent implements OnInit {

    profession: Profession;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private professionService: ProfessionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.profession.id !== undefined) {
            this.subscribeToSaveResponse(
                this.professionService.update(this.profession));
        } else {
            this.subscribeToSaveResponse(
                this.professionService.create(this.profession));
        }
    }

    private subscribeToSaveResponse(result: Observable<Profession>) {
        result.subscribe((res: Profession) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Profession) {
        this.eventManager.broadcast({ name: 'professionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-profession-popup',
    template: ''
})
export class ProfessionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private professionPopupService: ProfessionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.professionPopupService
                    .open(ProfessionDialogComponent as Component, params['id']);
            } else {
                this.professionPopupService
                    .open(ProfessionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
