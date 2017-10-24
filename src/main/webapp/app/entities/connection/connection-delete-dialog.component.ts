import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Connection } from './connection.model';
import { ConnectionPopupService } from './connection-popup.service';
import { ConnectionService } from './connection.service';

@Component({
    selector: 'jhi-connection-delete-dialog',
    templateUrl: './connection-delete-dialog.component.html'
})
export class ConnectionDeleteDialogComponent {

    connection: Connection;

    constructor(
        private connectionService: ConnectionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.connectionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'connectionListModification',
                content: 'Deleted an connection'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-connection-delete-popup',
    template: ''
})
export class ConnectionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private connectionPopupService: ConnectionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.connectionPopupService
                .open(ConnectionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
