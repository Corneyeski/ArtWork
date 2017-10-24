import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PricesTComponent } from './prices-t.component';
import { PricesTDetailComponent } from './prices-t-detail.component';
import { PricesTPopupComponent } from './prices-t-dialog.component';
import { PricesTDeletePopupComponent } from './prices-t-delete-dialog.component';

export const pricesTRoute: Routes = [
    {
        path: 'prices-t',
        component: PricesTComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.pricesT.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prices-t/:id',
        component: PricesTDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.pricesT.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pricesTPopupRoute: Routes = [
    {
        path: 'prices-t-new',
        component: PricesTPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.pricesT.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prices-t/:id/edit',
        component: PricesTPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.pricesT.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prices-t/:id/delete',
        component: PricesTDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.pricesT.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
