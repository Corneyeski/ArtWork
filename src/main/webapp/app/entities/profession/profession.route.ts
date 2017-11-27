import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProfessionComponent } from './profession.component';
import { ProfessionDetailComponent } from './profession-detail.component';
import { ProfessionPopupComponent } from './profession-dialog.component';
import { ProfessionDeletePopupComponent } from './profession-delete-dialog.component';

export const professionRoute: Routes = [
    {
        path: 'profession',
        component: ProfessionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.profession.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'profession/:id',
        component: ProfessionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.profession.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const professionPopupRoute: Routes = [
    {
        path: 'profession-new',
        component: ProfessionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.profession.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'profession/:id/edit',
        component: ProfessionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.profession.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'profession/:id/delete',
        component: ProfessionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.profession.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
