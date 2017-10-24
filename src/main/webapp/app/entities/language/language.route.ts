import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LanguageComponent } from './language.component';
import { LanguageDetailComponent } from './language-detail.component';
import { LanguagePopupComponent } from './language-dialog.component';
import { LanguageDeletePopupComponent } from './language-delete-dialog.component';

export const languageRoute: Routes = [
    {
        path: 'language',
        component: LanguageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.language.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'language/:id',
        component: LanguageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.language.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const languagePopupRoute: Routes = [
    {
        path: 'language-new',
        component: LanguagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.language.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'language/:id/edit',
        component: LanguagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.language.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'language/:id/delete',
        component: LanguageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.language.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
