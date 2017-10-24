import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ReportComponent } from './report.component';
import { ReportDetailComponent } from './report-detail.component';
import { ReportPopupComponent } from './report-dialog.component';
import { ReportDeletePopupComponent } from './report-delete-dialog.component';

export const reportRoute: Routes = [
    {
        path: 'report',
        component: ReportComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.report.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'report/:id',
        component: ReportDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.report.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reportPopupRoute: Routes = [
    {
        path: 'report-new',
        component: ReportPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.report.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'report/:id/edit',
        component: ReportPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.report.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'report/:id/delete',
        component: ReportDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.report.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
