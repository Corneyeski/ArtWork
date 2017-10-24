import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ReasonReport } from './reason-report.model';
import { ReasonReportPopupService } from './reason-report-popup.service';
import { ReasonReportService } from './reason-report.service';

@Component({
    selector: 'jhi-reason-report-delete-dialog',
    templateUrl: './reason-report-delete-dialog.component.html'
})
export class ReasonReportDeleteDialogComponent {

    reasonReport: ReasonReport;

    constructor(
        private reasonReportService: ReasonReportService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reasonReportService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'reasonReportListModification',
                content: 'Deleted an reasonReport'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reason-report-delete-popup',
    template: ''
})
export class ReasonReportDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reasonReportPopupService: ReasonReportPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.reasonReportPopupService
                .open(ReasonReportDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
