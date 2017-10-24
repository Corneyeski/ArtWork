import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { CommentLike } from './comment-like.model';
import { CommentLikeService } from './comment-like.service';

@Injectable()
export class CommentLikePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private commentLikeService: CommentLikeService

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
                this.commentLikeService.find(id).subscribe((commentLike) => {
                    commentLike.time = this.datePipe
                        .transform(commentLike.time, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.commentLikeModalRef(component, commentLike);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.commentLikeModalRef(component, new CommentLike());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    commentLikeModalRef(component: Component, commentLike: CommentLike): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.commentLike = commentLike;
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
