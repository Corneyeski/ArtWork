import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ValorationComponent } from './valoration.component';
import { ValorationDetailComponent } from './valoration-detail.component';
import { ValorationPopupComponent } from './valoration-dialog.component';
import { ValorationDeletePopupComponent } from './valoration-delete-dialog.component';

export const valorationRoute: Routes = [
    {
        path: 'valoration',
        component: ValorationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.valoration.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'valoration/:id',
        component: ValorationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.valoration.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const valorationPopupRoute: Routes = [
    {
        path: 'valoration-new',
        component: ValorationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.valoration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'valoration/:id/edit',
        component: ValorationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.valoration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'valoration/:id/delete',
        component: ValorationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.valoration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
