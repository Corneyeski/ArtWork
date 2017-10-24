import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtWorkSharedModule } from '../../shared';
import {
    ReasonReportService,
    ReasonReportPopupService,
    ReasonReportComponent,
    ReasonReportDetailComponent,
    ReasonReportDialogComponent,
    ReasonReportPopupComponent,
    ReasonReportDeletePopupComponent,
    ReasonReportDeleteDialogComponent,
    reasonReportRoute,
    reasonReportPopupRoute,
} from './';

const ENTITY_STATES = [
    ...reasonReportRoute,
    ...reasonReportPopupRoute,
];

@NgModule({
    imports: [
        ArtWorkSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReasonReportComponent,
        ReasonReportDetailComponent,
        ReasonReportDialogComponent,
        ReasonReportDeleteDialogComponent,
        ReasonReportPopupComponent,
        ReasonReportDeletePopupComponent,
    ],
    entryComponents: [
        ReasonReportComponent,
        ReasonReportDialogComponent,
        ReasonReportPopupComponent,
        ReasonReportDeleteDialogComponent,
        ReasonReportDeletePopupComponent,
    ],
    providers: [
        ReasonReportService,
        ReasonReportPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArtWorkReasonReportModule {}
