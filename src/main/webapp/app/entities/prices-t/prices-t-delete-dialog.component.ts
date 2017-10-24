import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PricesT } from './prices-t.model';
import { PricesTPopupService } from './prices-t-popup.service';
import { PricesTService } from './prices-t.service';

@Component({
    selector: 'jhi-prices-t-delete-dialog',
    templateUrl: './prices-t-delete-dialog.component.html'
})
export class PricesTDeleteDialogComponent {

    pricesT: PricesT;

    constructor(
        private pricesTService: PricesTService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pricesTService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pricesTListModification',
                content: 'Deleted an pricesT'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prices-t-delete-popup',
    template: ''
})
export class PricesTDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pricesTPopupService: PricesTPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pricesTPopupService
                .open(PricesTDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
