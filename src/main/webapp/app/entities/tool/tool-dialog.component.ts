import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Tool } from './tool.model';
import { ToolPopupService } from './tool-popup.service';
import { ToolService } from './tool.service';
import { UserExt, UserExtService } from '../user-ext';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-tool-dialog',
    templateUrl: './tool-dialog.component.html'
})
export class ToolDialogComponent implements OnInit {

    tool: Tool;
    isSaving: boolean;

    userexts: UserExt[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private toolService: ToolService,
        private userExtService: UserExtService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userExtService.query()
            .subscribe((res: ResponseWrapper) => { this.userexts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tool.id !== undefined) {
            this.subscribeToSaveResponse(
                this.toolService.update(this.tool));
        } else {
            this.subscribeToSaveResponse(
                this.toolService.create(this.tool));
        }
    }

    private subscribeToSaveResponse(result: Observable<Tool>) {
        result.subscribe((res: Tool) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Tool) {
        this.eventManager.broadcast({ name: 'toolListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserExtById(index: number, item: UserExt) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-tool-popup',
    template: ''
})
export class ToolPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private toolPopupService: ToolPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.toolPopupService
                    .open(ToolDialogComponent as Component, params['id']);
            } else {
                this.toolPopupService
                    .open(ToolDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
