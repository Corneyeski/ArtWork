import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ConnectionComponent } from './connection.component';
import { ConnectionDetailComponent } from './connection-detail.component';
import { ConnectionPopupComponent } from './connection-dialog.component';
import { ConnectionDeletePopupComponent } from './connection-delete-dialog.component';

export const connectionRoute: Routes = [
    {
        path: 'connection',
        component: ConnectionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.connection.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'connection/:id',
        component: ConnectionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.connection.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const connectionPopupRoute: Routes = [
    {
        path: 'connection-new',
        component: ConnectionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.connection.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'connection/:id/edit',
        component: ConnectionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.connection.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'connection/:id/delete',
        component: ConnectionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.connection.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
