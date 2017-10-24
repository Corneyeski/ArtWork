import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CommentLike } from './comment-like.model';
import { CommentLikeService } from './comment-like.service';

@Component({
    selector: 'jhi-comment-like-detail',
    templateUrl: './comment-like-detail.component.html'
})
export class CommentLikeDetailComponent implements OnInit, OnDestroy {

    commentLike: CommentLike;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private commentLikeService: CommentLikeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCommentLikes();
    }

    load(id) {
        this.commentLikeService.find(id).subscribe((commentLike) => {
            this.commentLike = commentLike;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCommentLikes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'commentLikeListModification',
            (response) => this.load(this.commentLike.id)
        );
    }
}
