import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CommentLike } from './comment-like.model';
import { CommentLikeService } from './comment-like.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-comment-like',
    templateUrl: './comment-like.component.html'
})
export class CommentLikeComponent implements OnInit, OnDestroy {
commentLikes: CommentLike[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private commentLikeService: CommentLikeService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.commentLikeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.commentLikes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCommentLikes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CommentLike) {
        return item.id;
    }
    registerChangeInCommentLikes() {
        this.eventSubscriber = this.eventManager.subscribe('commentLikeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
