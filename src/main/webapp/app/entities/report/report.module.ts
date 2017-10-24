import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtWorkSharedModule } from '../../shared';
import { ArtWorkAdminModule } from '../../admin/admin.module';
import {
    ReportService,
    ReportPopupService,
    ReportComponent,
    ReportDetailComponent,
    ReportDialogComponent,
    ReportPopupComponent,
    ReportDeletePopupComponent,
    ReportDeleteDialogComponent,
    reportRoute,
    reportPopupRoute,
} from './';

const ENTITY_STATES = [
    ...reportRoute,
    ...reportPopupRoute,
];

@NgModule({
    imports: [
        ArtWorkSharedModule,
        ArtWorkAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReportComponent,
        ReportDetailComponent,
        ReportDialogComponent,
        ReportDeleteDialogComponent,
        ReportPopupComponent,
        ReportDeletePopupComponent,
    ],
    entryComponents: [
        ReportComponent,
        ReportDialogComponent,
        ReportPopupComponent,
        ReportDeleteDialogComponent,
        ReportDeletePopupComponent,
    ],
    providers: [
        ReportService,
        ReportPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArtWorkReportModule {}
