import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MultimediaComponent } from './multimedia.component';
import { MultimediaDetailComponent } from './multimedia-detail.component';
import { MultimediaPopupComponent } from './multimedia-dialog.component';
import { MultimediaDeletePopupComponent } from './multimedia-delete-dialog.component';

export const multimediaRoute: Routes = [
    {
        path: 'multimedia',
        component: MultimediaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.multimedia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'multimedia/:id',
        component: MultimediaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.multimedia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const multimediaPopupRoute: Routes = [
    {
        path: 'multimedia-new',
        component: MultimediaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.multimedia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'multimedia/:id/edit',
        component: MultimediaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.multimedia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'multimedia/:id/delete',
        component: MultimediaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.multimedia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
