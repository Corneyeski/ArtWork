import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FollowingComponent } from './following.component';
import { FollowingDetailComponent } from './following-detail.component';
import { FollowingPopupComponent } from './following-dialog.component';
import { FollowingDeletePopupComponent } from './following-delete-dialog.component';

export const followingRoute: Routes = [
    {
        path: 'following',
        component: FollowingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.following.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'following/:id',
        component: FollowingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.following.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const followingPopupRoute: Routes = [
    {
        path: 'following-new',
        component: FollowingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.following.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'following/:id/edit',
        component: FollowingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.following.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'following/:id/delete',
        component: FollowingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.following.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
