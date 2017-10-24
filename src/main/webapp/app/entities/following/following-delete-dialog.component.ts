import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Following } from './following.model';
import { FollowingPopupService } from './following-popup.service';
import { FollowingService } from './following.service';

@Component({
    selector: 'jhi-following-delete-dialog',
    templateUrl: './following-delete-dialog.component.html'
})
export class FollowingDeleteDialogComponent {

    following: Following;

    constructor(
        private followingService: FollowingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.followingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'followingListModification',
                content: 'Deleted an following'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-following-delete-popup',
    template: ''
})
export class FollowingDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private followingPopupService: FollowingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.followingPopupService
                .open(FollowingDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
