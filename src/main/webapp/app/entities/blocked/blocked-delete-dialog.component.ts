import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Blocked } from './blocked.model';
import { BlockedPopupService } from './blocked-popup.service';
import { BlockedService } from './blocked.service';

@Component({
    selector: 'jhi-blocked-delete-dialog',
    templateUrl: './blocked-delete-dialog.component.html'
})
export class BlockedDeleteDialogComponent {

    blocked: Blocked;

    constructor(
        private blockedService: BlockedService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.blockedService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'blockedListModification',
                content: 'Deleted an blocked'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-blocked-delete-popup',
    template: ''
})
export class BlockedDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private blockedPopupService: BlockedPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.blockedPopupService
                .open(BlockedDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
