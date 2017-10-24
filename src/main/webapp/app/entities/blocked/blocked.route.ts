import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BlockedComponent } from './blocked.component';
import { BlockedDetailComponent } from './blocked-detail.component';
import { BlockedPopupComponent } from './blocked-dialog.component';
import { BlockedDeletePopupComponent } from './blocked-delete-dialog.component';

export const blockedRoute: Routes = [
    {
        path: 'blocked',
        component: BlockedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.blocked.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'blocked/:id',
        component: BlockedDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.blocked.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const blockedPopupRoute: Routes = [
    {
        path: 'blocked-new',
        component: BlockedPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.blocked.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'blocked/:id/edit',
        component: BlockedPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.blocked.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'blocked/:id/delete',
        component: BlockedDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.blocked.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
