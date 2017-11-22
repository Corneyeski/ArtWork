import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Multimedia } from './multimedia.model';
import { MultimediaPopupService } from './multimedia-popup.service';
import { MultimediaService } from './multimedia.service';

@Component({
    selector: 'jhi-multimedia-delete-dialog',
    templateUrl: './multimedia-delete-dialog.component.html'
})
export class MultimediaDeleteDialogComponent {

    multimedia: Multimedia;

    constructor(
        private multimediaService: MultimediaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.multimediaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'multimediaListModification',
                content: 'Deleted an multimedia'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-multimedia-delete-popup',
    template: ''
})
export class MultimediaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private multimediaPopupService: MultimediaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.multimediaPopupService
                .open(MultimediaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
