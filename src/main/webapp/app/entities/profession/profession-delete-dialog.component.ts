import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Profession } from './profession.model';
import { ProfessionPopupService } from './profession-popup.service';
import { ProfessionService } from './profession.service';

@Component({
    selector: 'jhi-profession-delete-dialog',
    templateUrl: './profession-delete-dialog.component.html'
})
export class ProfessionDeleteDialogComponent {

    profession: Profession;

    constructor(
        private professionService: ProfessionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.professionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'professionListModification',
                content: 'Deleted an profession'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-profession-delete-popup',
    template: ''
})
export class ProfessionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private professionPopupService: ProfessionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.professionPopupService
                .open(ProfessionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
