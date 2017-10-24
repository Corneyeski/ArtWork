import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ResumeCreation } from './resume-creation.model';
import { ResumeCreationPopupService } from './resume-creation-popup.service';
import { ResumeCreationService } from './resume-creation.service';

@Component({
    selector: 'jhi-resume-creation-delete-dialog',
    templateUrl: './resume-creation-delete-dialog.component.html'
})
export class ResumeCreationDeleteDialogComponent {

    resumeCreation: ResumeCreation;

    constructor(
        private resumeCreationService: ResumeCreationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.resumeCreationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'resumeCreationListModification',
                content: 'Deleted an resumeCreation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-resume-creation-delete-popup',
    template: ''
})
export class ResumeCreationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private resumeCreationPopupService: ResumeCreationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.resumeCreationPopupService
                .open(ResumeCreationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
