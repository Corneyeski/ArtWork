import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TrainingComponent } from './training.component';
import { TrainingDetailComponent } from './training-detail.component';
import { TrainingPopupComponent } from './training-dialog.component';
import { TrainingDeletePopupComponent } from './training-delete-dialog.component';

export const trainingRoute: Routes = [
    {
        path: 'training',
        component: TrainingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.training.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'training/:id',
        component: TrainingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.training.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const trainingPopupRoute: Routes = [
    {
        path: 'training-new',
        component: TrainingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.training.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'training/:id/edit',
        component: TrainingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.training.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'training/:id/delete',
        component: TrainingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.training.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
