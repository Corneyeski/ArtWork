import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ResumeCreationComponent } from './resume-creation.component';
import { ResumeCreationDetailComponent } from './resume-creation-detail.component';
import { ResumeCreationPopupComponent } from './resume-creation-dialog.component';
import { ResumeCreationDeletePopupComponent } from './resume-creation-delete-dialog.component';

export const resumeCreationRoute: Routes = [
    {
        path: 'resume-creation',
        component: ResumeCreationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.resumeCreation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'resume-creation/:id',
        component: ResumeCreationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.resumeCreation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const resumeCreationPopupRoute: Routes = [
    {
        path: 'resume-creation-new',
        component: ResumeCreationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.resumeCreation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'resume-creation/:id/edit',
        component: ResumeCreationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.resumeCreation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'resume-creation/:id/delete',
        component: ResumeCreationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.resumeCreation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
