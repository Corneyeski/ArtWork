import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Valoration } from './valoration.model';
import { ValorationPopupService } from './valoration-popup.service';
import { ValorationService } from './valoration.service';

@Component({
    selector: 'jhi-valoration-delete-dialog',
    templateUrl: './valoration-delete-dialog.component.html'
})
export class ValorationDeleteDialogComponent {

    valoration: Valoration;

    constructor(
        private valorationService: ValorationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.valorationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'valorationListModification',
                content: 'Deleted an valoration'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-valoration-delete-popup',
    template: ''
})
export class ValorationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private valorationPopupService: ValorationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.valorationPopupService
                .open(ValorationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
