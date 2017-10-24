import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CommentLike } from './comment-like.model';
import { CommentLikePopupService } from './comment-like-popup.service';
import { CommentLikeService } from './comment-like.service';
import { User, UserService } from '../../shared';
import { Comment, CommentService } from '../comment';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-comment-like-dialog',
    templateUrl: './comment-like-dialog.component.html'
})
export class CommentLikeDialogComponent implements OnInit {

    commentLike: CommentLike;
    isSaving: boolean;

    users: User[];

    comments: Comment[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private commentLikeService: CommentLikeService,
        private userService: UserService,
        private commentService: CommentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.commentService.query()
            .subscribe((res: ResponseWrapper) => { this.comments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.commentLike.id !== undefined) {
            this.subscribeToSaveResponse(
                this.commentLikeService.update(this.commentLike));
        } else {
            this.subscribeToSaveResponse(
                this.commentLikeService.create(this.commentLike));
        }
    }

    private subscribeToSaveResponse(result: Observable<CommentLike>) {
        result.subscribe((res: CommentLike) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CommentLike) {
        this.eventManager.broadcast({ name: 'commentLikeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackCommentById(index: number, item: Comment) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-comment-like-popup',
    template: ''
})
export class CommentLikePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commentLikePopupService: CommentLikePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.commentLikePopupService
                    .open(CommentLikeDialogComponent as Component, params['id']);
            } else {
                this.commentLikePopupService
                    .open(CommentLikeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
