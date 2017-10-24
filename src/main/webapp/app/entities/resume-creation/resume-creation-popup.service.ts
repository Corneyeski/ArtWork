import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ResumeCreation } from './resume-creation.model';
import { ResumeCreationService } from './resume-creation.service';

@Injectable()
export class ResumeCreationPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private resumeCreationService: ResumeCreationService

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
                this.resumeCreationService.find(id).subscribe((resumeCreation) => {
                    if (resumeCreation.birthdate) {
                        resumeCreation.birthdate = {
                            year: resumeCreation.birthdate.getFullYear(),
                            month: resumeCreation.birthdate.getMonth() + 1,
                            day: resumeCreation.birthdate.getDate()
                        };
                    }
                    this.ngbModalRef = this.resumeCreationModalRef(component, resumeCreation);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.resumeCreationModalRef(component, new ResumeCreation());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    resumeCreationModalRef(component: Component, resumeCreation: ResumeCreation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.resumeCreation = resumeCreation;
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
