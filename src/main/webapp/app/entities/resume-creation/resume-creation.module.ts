import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtWorkSharedModule } from '../../shared';
import {
    ResumeCreationService,
    ResumeCreationPopupService,
    ResumeCreationComponent,
    ResumeCreationDetailComponent,
    ResumeCreationDialogComponent,
    ResumeCreationPopupComponent,
    ResumeCreationDeletePopupComponent,
    ResumeCreationDeleteDialogComponent,
    resumeCreationRoute,
    resumeCreationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...resumeCreationRoute,
    ...resumeCreationPopupRoute,
];

@NgModule({
    imports: [
        ArtWorkSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ResumeCreationComponent,
        ResumeCreationDetailComponent,
        ResumeCreationDialogComponent,
        ResumeCreationDeleteDialogComponent,
        ResumeCreationPopupComponent,
        ResumeCreationDeletePopupComponent,
    ],
    entryComponents: [
        ResumeCreationComponent,
        ResumeCreationDialogComponent,
        ResumeCreationPopupComponent,
        ResumeCreationDeleteDialogComponent,
        ResumeCreationDeletePopupComponent,
    ],
    providers: [
        ResumeCreationService,
        ResumeCreationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArtWorkResumeCreationModule {}
