import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserProfileValorationComponent } from './user-profile-valoration.component';
import { UserProfileValorationDetailComponent } from './user-profile-valoration-detail.component';
import { UserProfileValorationPopupComponent } from './user-profile-valoration-dialog.component';
import { UserProfileValorationDeletePopupComponent } from './user-profile-valoration-delete-dialog.component';

export const userProfileValorationRoute: Routes = [
    {
        path: 'user-profile-valoration',
        component: UserProfileValorationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.userProfileValoration.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-profile-valoration/:id',
        component: UserProfileValorationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.userProfileValoration.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userProfileValorationPopupRoute: Routes = [
    {
        path: 'user-profile-valoration-new',
        component: UserProfileValorationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.userProfileValoration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-profile-valoration/:id/edit',
        component: UserProfileValorationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.userProfileValoration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-profile-valoration/:id/delete',
        component: UserProfileValorationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.userProfileValoration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
