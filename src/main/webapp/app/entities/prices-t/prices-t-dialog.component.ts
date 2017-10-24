import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PricesT } from './prices-t.model';
import { PricesTPopupService } from './prices-t-popup.service';
import { PricesTService } from './prices-t.service';
import { Tool, ToolService } from '../tool';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-prices-t-dialog',
    templateUrl: './prices-t-dialog.component.html'
})
export class PricesTDialogComponent implements OnInit {

    pricesT: PricesT;
    isSaving: boolean;

    tools: Tool[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private pricesTService: PricesTService,
        private toolService: ToolService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.toolService
            .query({filter: 'pricest-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.pricesT.tool || !this.pricesT.tool.id) {
                    this.tools = res.json;
                } else {
                    this.toolService
                        .find(this.pricesT.tool.id)
                        .subscribe((subRes: Tool) => {
                            this.tools = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pricesT.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pricesTService.update(this.pricesT));
        } else {
            this.subscribeToSaveResponse(
                this.pricesTService.create(this.pricesT));
        }
    }

    private subscribeToSaveResponse(result: Observable<PricesT>) {
        result.subscribe((res: PricesT) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PricesT) {
        this.eventManager.broadcast({ name: 'pricesTListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackToolById(index: number, item: Tool) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-prices-t-popup',
    template: ''
})
export class PricesTPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pricesTPopupService: PricesTPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pricesTPopupService
                    .open(PricesTDialogComponent as Component, params['id']);
            } else {
                this.pricesTPopupService
                    .open(PricesTDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
