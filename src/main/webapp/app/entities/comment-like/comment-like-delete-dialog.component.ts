import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CommentLike } from './comment-like.model';
import { CommentLikePopupService } from './comment-like-popup.service';
import { CommentLikeService } from './comment-like.service';

@Component({
    selector: 'jhi-comment-like-delete-dialog',
    templateUrl: './comment-like-delete-dialog.component.html'
})
export class CommentLikeDeleteDialogComponent {

    commentLike: CommentLike;

    constructor(
        private commentLikeService: CommentLikeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commentLikeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'commentLikeListModification',
                content: 'Deleted an commentLike'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-comment-like-delete-popup',
    template: ''
})
export class CommentLikeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commentLikePopupService: CommentLikePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.commentLikePopupService
                .open(CommentLikeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
