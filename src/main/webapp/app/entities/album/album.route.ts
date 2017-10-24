import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AlbumComponent } from './album.component';
import { AlbumDetailComponent } from './album-detail.component';
import { AlbumPopupComponent } from './album-dialog.component';
import { AlbumDeletePopupComponent } from './album-delete-dialog.component';

export const albumRoute: Routes = [
    {
        path: 'album',
        component: AlbumComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.album.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'album/:id',
        component: AlbumDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.album.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const albumPopupRoute: Routes = [
    {
        path: 'album-new',
        component: AlbumPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.album.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'album/:id/edit',
        component: AlbumPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.album.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'album/:id/delete',
        component: AlbumDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.album.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
