import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ExperienceComponent } from './experience.component';
import { ExperienceDetailComponent } from './experience-detail.component';
import { ExperiencePopupComponent } from './experience-dialog.component';
import { ExperienceDeletePopupComponent } from './experience-delete-dialog.component';

export const experienceRoute: Routes = [
    {
        path: 'experience',
        component: ExperienceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.experience.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'experience/:id',
        component: ExperienceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.experience.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const experiencePopupRoute: Routes = [
    {
        path: 'experience-new',
        component: ExperiencePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.experience.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'experience/:id/edit',
        component: ExperiencePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.experience.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'experience/:id/delete',
        component: ExperienceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.experience.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
