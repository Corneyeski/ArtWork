import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CommentLikeComponent } from './comment-like.component';
import { CommentLikeDetailComponent } from './comment-like-detail.component';
import { CommentLikePopupComponent } from './comment-like-dialog.component';
import { CommentLikeDeletePopupComponent } from './comment-like-delete-dialog.component';

export const commentLikeRoute: Routes = [
    {
        path: 'comment-like',
        component: CommentLikeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.commentLike.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'comment-like/:id',
        component: CommentLikeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.commentLike.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commentLikePopupRoute: Routes = [
    {
        path: 'comment-like-new',
        component: CommentLikePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.commentLike.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'comment-like/:id/edit',
        component: CommentLikePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.commentLike.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'comment-like/:id/delete',
        component: CommentLikeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.commentLike.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
