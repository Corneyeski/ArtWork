import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserProfileValoration } from './user-profile-valoration.model';
import { UserProfileValorationPopupService } from './user-profile-valoration-popup.service';
import { UserProfileValorationService } from './user-profile-valoration.service';

@Component({
    selector: 'jhi-user-profile-valoration-delete-dialog',
    templateUrl: './user-profile-valoration-delete-dialog.component.html'
})
export class UserProfileValorationDeleteDialogComponent {

    userProfileValoration: UserProfileValoration;

    constructor(
        private userProfileValorationService: UserProfileValorationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userProfileValorationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userProfileValorationListModification',
                content: 'Deleted an userProfileValoration'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-profile-valoration-delete-popup',
    template: ''
})
export class UserProfileValorationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userProfileValorationPopupService: UserProfileValorationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userProfileValorationPopupService
                .open(UserProfileValorationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
