import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ReasonReportComponent } from './reason-report.component';
import { ReasonReportDetailComponent } from './reason-report-detail.component';
import { ReasonReportPopupComponent } from './reason-report-dialog.component';
import { ReasonReportDeletePopupComponent } from './reason-report-delete-dialog.component';

export const reasonReportRoute: Routes = [
    {
        path: 'reason-report',
        component: ReasonReportComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.reasonReport.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'reason-report/:id',
        component: ReasonReportDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.reasonReport.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reasonReportPopupRoute: Routes = [
    {
        path: 'reason-report-new',
        component: ReasonReportPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.reasonReport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reason-report/:id/edit',
        component: ReasonReportPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.reasonReport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reason-report/:id/delete',
        component: ReasonReportDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'artWorkApp.reasonReport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
