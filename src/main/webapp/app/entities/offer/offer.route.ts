import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OfferComponent } from './offer.component';
import { OfferDetailComponent } from './offer-detail.component';
import { OfferPopupComponent } from './offer-dialog.component';
import { OfferDeletePopupComponent } from './offer-delete-dialog.component';

export const offerRoute: Routes = [
    {
        path: 'offer',
        component: OfferComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.offer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'offer/:id',
        component: OfferDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.offer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const offerPopupRoute: Routes = [
    {
        path: 'offer-new',
        component: OfferPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.offer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'offer/:id/edit',
        component: OfferPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.offer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'offer/:id/delete',
        component: OfferDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.offer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
