import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { UserProfileValoration } from './user-profile-valoration.model';
import { UserProfileValorationService } from './user-profile-valoration.service';

@Injectable()
export class UserProfileValorationPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private userProfileValorationService: UserProfileValorationService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.userProfileValorationService.find(id).subscribe((userProfileValoration) => {
                    this.ngbModalRef = this.userProfileValorationModalRef(component, userProfileValoration);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.userProfileValorationModalRef(component, new UserProfileValoration());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    userProfileValorationModalRef(component: Component, userProfileValoration: UserProfileValoration): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.userProfileValoration = userProfileValoration;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
